// 取得所有具有 toggle 類別的元素（公司名稱）
var toggles = document.querySelectorAll('.toggle');

// 將點擊事件添加到每個 toggle 元素上
toggles.forEach(function(toggle) {
    toggle.addEventListener('click', function() {
        // 找到下一個 tr 元素（詳細資訊）並切換其顯示狀態
        var details = this.parentElement.nextElementSibling;
        details.classList.toggle('show');
    });
});
