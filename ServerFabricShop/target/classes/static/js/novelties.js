let currentCategory = 'tkani';

// Функция для переключения категорий
function switchCategory(category, event) {
    currentCategory = category;
    
    // Обновляем активные кнопки
    document.querySelectorAll('.tab-button').forEach(button => {
        button.classList.remove('active');
    });
    
    // Находим активную кнопку по категории
    document.querySelectorAll('.tab-button').forEach(button => {
        if (button.textContent.toLowerCase().includes(category)) {
            button.classList.add('active');
        }
    });
    
    // Обновляем заголовки
    const title = document.getElementById('category-title');
    const description = document.getElementById('category-description');
    
    if (category === 'tkani') {
        title.textContent = 'Новинки тканей';
        description.textContent = 'Самые свежие поступления тканей в нашем магазине';
    } else {
        title.textContent = 'Новинки фурнитуры';
        description.textContent = 'Новые поступления фурнитуры и аксессуаров';
    }
    
    // Загружаем данные для выбранной категории
    loadNovelties();
}

// Функция для загрузки новинок
async function loadNovelties() {
    try {
        const endpoint = currentCategory === 'tkani' 
            ? "http://localhost:8080/atelie/allTkanNovinki"
            : "http://localhost:8080/atelie/allFurnituraNovinki";
            
        console.log('Загрузка данных из:', endpoint);
        const response = await fetch(endpoint);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        console.log('Получены данные:', data);
        
        const container = document.getElementById('novelties-container');
        container.innerHTML = '';
        
        if (data.length === 0) {
            container.innerHTML = '<div class="loading">Новинки временно отсутствуют</div>';
            return;
        }
        
        data.forEach(item => {
            const card = document.createElement('div');
            card.className = 'novelty-card';
            
            // Используем фото из базы данных
            const imageHtml = item.foto 
                ? `<img src="${item.foto}" alt="${item.nazvanie}" class="novelty-photo">`
                : `<div class="novelty-placeholder">${currentCategory === 'tkani' ? '🧵' : '🔘'}</div>`;
            
            card.innerHTML = `
                <div class="novelty-image">
                    ${imageHtml}
                </div>
                <div class="novelty-info">
                    <h3>${item.nazvanie || 'Без названия'}</h3>
                    ${item.artikul ? `<p>Артикул: ${item.artikul}</p>` : ''}
                    <div class="novelty-meta">
                        ${item.kategoriya ? `<span>${item.kategoriya}</span>` : ''}
                        <span class="novelty-badge">Новинка</span>
                    </div>
                </div>
            `;
            
            container.appendChild(card);
        });
    } catch(error) {
        console.error('Ошибка загрузки:', error);
        document.getElementById('novelties-container').innerHTML = 
            '<div class="loading">Ошибка загрузки данных. Пожалуйста, попробуйте позже.</div>';
    }
}

// Загрузка новинок при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    // Устанавливаем обработчики для кнопок
    document.querySelectorAll('.tab-button').forEach(button => {
        button.addEventListener('click', function() {
            const category = this.textContent.toLowerCase().includes('фурнитур') ? 'furnitura' : 'tkani';
            switchCategory(category);
        });
    });
    
    // Загружаем начальные данные
    loadNovelties();
});