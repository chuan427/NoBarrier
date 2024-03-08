const notificationIcon = document.getElementById('notificationIcon');
const notificationDropdown = document.getElementById('notificationDropdown');

notificationIcon.addEventListener('click', function() {
    notificationDropdown.classList.toggle('show');
});

async function getNotifications() {
    const response = await fetch('http://localhost:3000/notifications');
    const notifications = await response.json();
    const notificationsDiv = document.getElementById('notifications');
    notificationsDiv.innerHTML = notifications.map(notification => `
        <div>
            <h3>${notification.title}</h3>
            <p>${notification.content}</p>
        </div>
    `).join('');
}

getNotifications();