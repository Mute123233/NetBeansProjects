// Общие функции для всех страниц
document.addEventListener('DOMContentLoaded', function() {
    // Обработка формы авторизации
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const login = document.getElementById('login').value;
            const password = document.getElementById('password').value;
            
            // Здесь должна быть логика авторизации
            alert(`Попытка входа с логином: ${login}`);
            // После успешной авторизации можно перенаправить на другую страницу
        });
    }
});