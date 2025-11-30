// Функция для загрузки информации о заказах
async function loadOrderStatus(orderNumber) {
    try {
        const response = await fetch("http://localhost:8080/atelie/ZakazInfo", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `id=${orderNumber || ''}`
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const orders = await response.json();
        console.log('Полученные данные:', orders);
        
        const orderDetails = document.getElementById('order-details');
        
        // Если номер заказа указан, ищем конкретный заказ
        if (orderNumber && orders.length > 0) {
            // Поскольку в DTO нет поля id, используем индекс как номер заказа
            const orderIndex = parseInt(orderNumber) - 1;
            const order = orders[orderIndex] || orders[0];
            displayOrderDetails(order, orderNumber);
        } 
        // Если номер не указан, показываем все заказы
        else if (orders.length > 0) {
            displayAllOrders(orders);
        } 
        else {
            orderDetails.innerHTML = `
                <div class="order-details">
                    <p>Заказы не найдены</p>
                </div>
            `;
        }
        
    } catch(error) {
        console.error('Ошибка загрузки данных:', error);
        document.getElementById('order-details').innerHTML = 
            '<div class="order-details"><p>Ошибка загрузки данных. Пожалуйста, попробуйте снова.</p></div>';
    }
}

// Функция для отображения деталей одного заказа
function displayOrderDetails(orderData, orderNumber) {
    const orderDetails = document.getElementById('order-details');
    
    // Получаем информацию о статусе и таймлайне
    const statusInfo = getStatusInfo(orderData.statusZakaza, orderData.dataZakaza);
    
    orderDetails.innerHTML = `
        <div class="order-details">
            <div class="order-header">
                <h3>Заказ №${orderNumber}</h3>
                <span class="order-status ${statusInfo.statusClass}">${orderData.statusZakaza || 'Статус не указан'}</span>
            </div>
            <div class="order-info">
                <p><strong>Клиент:</strong> ${orderData.fioKlienta || 'Не указан'}</p>
                <p><strong>Изделие:</strong> ${orderData.izdelieKlienta || 'Не указано'}</p>
                <p><strong>Дата заказа:</strong> ${orderData.dataZakaza || 'Не указана'}</p>
                <p><strong>Стоимость:</strong> ${orderData.tsenaZakaza ? orderData.tsenaZakaza + ' руб.' : 'Не рассчитана'}</p>
            </div>
            <div class="order-timeline">
                <h4>Ход выполнения заказа:</h4>
                ${statusInfo.timeline.map((item, index) => `
                    <div class="timeline-item ${item.completed ? 'completed' : ''} ${item.current ? 'active' : ''}">
                        <div class="timeline-dot"></div>
                        <div class="timeline-content">
                            <h5>${item.step}</h5>
                            <p>${item.date || 'Ожидается'}</p>
                        </div>
                    </div>
                `).join('')}
            </div>
        </div>
    `;
}

// Функция для отображения всех заказов
function displayAllOrders(orders) {
    const orderDetails = document.getElementById('order-details');
    
    const ordersHtml = orders.map((order, index) => {
        const statusInfo = getStatusInfo(order.statusZakaza);
        return `
            <div class="order-card">
                <div class="order-header">
                    <h3>Заказ №${index + 1}</h3>
                    <span class="order-status ${statusInfo.statusClass}">${order.statusZakaza || 'Статус не указан'}</span>
                </div>
                <div class="order-info">
                    <p><strong>Клиент:</strong> ${order.fioKlienta || 'Не указан'}</p>
                    <p><strong>Изделие:</strong> ${order.izdelieKlienta || 'Не указано'}</p>
                    <p><strong>Дата заказа:</strong> ${order.dataZakaza || 'Не указана'}</p>
                    <p><strong>Стоимость:</strong> ${order.tsenaZakaza ? order.tsenaZakaza + ' руб.' : 'Не рассчитана'}</p>
                </div>
            </div>
        `;
    }).join('');
    
    orderDetails.innerHTML = `
        <div class="orders-list">
            <h3>Все заказы (${orders.length})</h3>
            ${ordersHtml}
        </div>
    `;
}

// Функция для получения информации о статусе и таймлайне
function getStatusInfo(status, orderDate) {
    let statusClass = 'status-pending';
    let timeline = [];
    
    // Определяем CSS класс на основе статуса
    if (status) {
        status = status.toLowerCase();
        if (status.includes('принят') || status.includes('ожидан')) {
            statusClass = 'status-pending';
        } else if (status.includes('работ') || status.includes('выполн')) {
            statusClass = 'status-in-progress';
        } else if (status.includes('готов') || status.includes('заверш')) {
            statusClass = 'status-completed';
        } else if (status.includes('отмен') || status.includes('отказ')) {
            statusClass = 'status-cancelled';
        }
    }
    
    // Создаем таймлайн на основе статуса
    const baseTimeline = [
        { step: 'Заказ принят', completed: true, current: false },
        { step: 'Заказ в работе', completed: false, current: false },
        { step: 'Готов к выдаче', completed: false, current: false },
        { step: 'Заказ выдан', completed: false, current: false }
    ];
    
    // Заполняем таймлайн в зависимости от статуса
    if (status) {
        status = status.toLowerCase();
        if (status.includes('принят')) {
            baseTimeline[0].completed = true;
            baseTimeline[1].current = true;
        } else if (status.includes('работ')) {
            baseTimeline[0].completed = true;
            baseTimeline[1].completed = true;
            baseTimeline[2].current = true;
        } else if (status.includes('готов')) {
            baseTimeline[0].completed = true;
            baseTimeline[1].completed = true;
            baseTimeline[2].completed = true;
            baseTimeline[3].current = true;
        } else if (status.includes('выдан') || status.includes('заверш')) {
            baseTimeline.forEach(item => {
                item.completed = true;
                item.current = false;
            });
        } else if (status.includes('отмен')) {
            baseTimeline.forEach(item => {
                item.completed = false;
                item.current = false;
            });
            baseTimeline[0].completed = true;
        }
    }
    
    // Добавляем даты к этапам
    timeline = baseTimeline.map((item, index) => ({
        ...item,
        date: index === 0 ? orderDate : ''
    }));
    
    return {
        statusClass,
        timeline
    };
}

// Обработка поиска заказа
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('search-order').addEventListener('click', function() {
        const orderNumber = document.getElementById('order-number').value;
        if (orderNumber) {
            loadOrderStatus(orderNumber);
        } else {
            alert('Пожалуйста, введите номер заказа');
        }
    });
    
    // Обработка нажатия Enter в поле ввода
    document.getElementById('order-number').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            document.getElementById('search-order').click();
        }
    });
    
    // Загрузка всех заказов при старте (опционально)
    // loadOrderStatus('');
});