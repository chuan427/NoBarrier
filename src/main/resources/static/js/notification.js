// 監聽滾動事件
window.addEventListener("scroll", function() {
    // 檢查滾動距離，如果超過 20px，顯示返回頂部按鈕，否則隱藏
    if (window.pageYOffset > 20) {
        document.getElementById("back-to-top-btn").style.display = "block";
    } else {
        document.getElementById("back-to-top-btn").style.display = "none";
    }
});

// 獲取通知圖標和下拉菜單
const notificationIcon = document.getElementById('notificationIcon');
const notificationDropdown = document.getElementById('notificationDropdown');

// 點擊通知圖標時，切換通知下拉菜單的顯示和隱藏
notificationIcon.addEventListener('click', function() {
    notificationDropdown.classList.toggle('show');
});

// 異步函數，獲取通知數據並動態添加到網頁中
async function getNotifications() {
    try {
        const response = await fetch('http://localhost:3000/notifications');
        const notifications = await response.json();

        // 檢查 notifications 是否為數組
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

// 調用獲取通知數據的函數
getNotifications();
