// 监听滚动事件
window.addEventListener("scroll", function() {
    // 检查滚动距离，如果超过 20px，显示返回顶部按钮，否则隐藏
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("back-to-top-btn").style.display = "block";
    } else {
        document.getElementById("back-to-top-btn").style.display = "none";
    }
});

// 获取通知图标和下拉菜单
const notificationIcon = document.getElementById('notificationIcon');
const notificationDropdown = document.getElementById('notificationDropdown');

// 点击通知图标时，切换通知下拉菜单的显示和隐藏
notificationIcon.addEventListener('click', function() {
    notificationDropdown.classList.toggle('show');
});

// 异步函数，获取通知数据并动态添加到页面中
async function getNotifications() {
    try {
        const response = await fetch('http://localhost:3000/notifications');
        const notifications = await response.json();

        // 检查 notifications 是否为数组
        if (Array.isArray(notifications)) {
            const notificationsDiv = document.getElementById('notifications');
            notificationsDiv.innerHTML = notifications.map(notification => `
                <div>
                    <h3>${notification.title}</h3>
                    <p>${notification.content}</p>
                </div>
            `).join('');
        } else {
            console.error('Notifications is not an array:', notifications);
        }
    } catch (error) {
        console.error('Error fetching notifications:', error);
    }
}


// 调用获取通知数据的函数
getNotifications();
