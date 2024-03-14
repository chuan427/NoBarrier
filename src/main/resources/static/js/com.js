document.addEventListener('DOMContentLoaded', function() {
  

const itemsPerPage = 7; // 每頁顯示的數量
let currentPage = 1; // 當前頁數
const data = [
    { id: 1, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '照明配件', name1: '詳情', link: 'com_homepage.html'},
    { id: 2, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '工業加工', name1: '詳情', link: 'com_homepage.html'},
    { id: 3, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '金屬物件', name1: '詳情', link: 'com_homepage.html'},
    { id: 4, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '機械材料', name1: '詳情', link: 'com_homepage.html'},
    { id: 5, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '電子元件', name1: '詳情', link: 'com_homepage.html'},
    { id: 6, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '照明配件', name1: '詳情', link: 'com_homepage.html'},
    { id: 7, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '工業加工', name1: '詳情', link: 'com_homepage.html'},
    { id: 8, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '塑膠零件', name1: '詳情', link: 'com_homepage.html'},
    { id: 9, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '工業加工', name1: '詳情', link: 'com_homepage.html'},
    { id: 10, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '電子元件', name1: '詳情', link: 'com_homepage.html'},
    { id: 11, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '機械材料', name1: '詳情', link: 'com_homepage.html'},
    { id: 12, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '金屬物件', name1: '詳情', link: 'com_homepage.html'},
    { id: 13, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '金屬物件', name1: '詳情', link: 'com_homepage.html'},
    { id: 14, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '螺絲和緊固件', name1: '詳情', link: 'com_homepage.html'},
    { id: 15, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '其他', name1: '詳情', link: 'com_homepage.html'},
    { id: 16, name: 'xxx有限公司', add: '台北市中山區南京東路xx號', classList: '塑膠零件', name1: '詳情', link: 'com_homepage.html'}
];

function renderTable(page) {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const tableBody = document.getElementById('table-body');
    tableBody.innerHTML = '';
    for (let i = startIndex; i < endIndex && i < data.length; i++) {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${data[i].id}</td><td>${data[i].name}</td><td>${data[i].add}</td><td>${data[i].classList}</td><td><a href="${data[i].link}">${data[i].name1}</a></td>`;
        tableBody.appendChild(row);
    }
}

function renderPagination() {
    const totalPages = Math.ceil(data.length / itemsPerPage);
    const pagination = document.getElementById('pagination').querySelector('.pagination');
    pagination.innerHTML = '';
    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement('li');
        li.className = 'page-item';
        const a = document.createElement('a');
        a.className = 'page-link';
        a.innerText = i;
        a.href = '#';
        a.addEventListener('click', function() {
            currentPage = i;
            renderTable(currentPage);
            highlightCurrentPage();
            const table = document.getElementById('table-body');
            table.scrollIntoView({ behavior: 'smooth', block: 'start' });
        });
        
        li.appendChild(a);
        pagination.appendChild(li);
    }
    highlightCurrentPage();
}

function highlightCurrentPage() {
    const pagination = document.getElementById('pagination').querySelector('.pagination');
    const pages = pagination.querySelectorAll('.page-link');
    pages.forEach(page => {
        if (parseInt(page.innerText) === currentPage) {
            page.parentNode.classList.add('active');
        } else {
            page.parentNode.classList.remove('active');
        }
    });
}

renderTable(currentPage);
renderPagination();

document.addEventListener("scroll", function() {
    var button = document.getElementById("back-to-top-btn");
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        button.style.display = "block";
    } else {
        button.style.display = "none";
    }
});

document.getElementById("back-to-top-btn").addEventListener("click", function() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
});

document.getElementById("open-chat-button").addEventListener("click", function() {
    document.getElementById("chat-box").style.display = "block";
});

document.getElementById("close-chat-button").addEventListener("click", function() {
    document.getElementById("chat-box").style.display = "none";
});

document.getElementById("back-to-top-btn").addEventListener("click", function() {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});

document.getElementById("send-message-button").addEventListener("click", function() {
    var message = document.getElementById("message-input").value;
    if (message.trim() !== "") {
        // 在這裡處理送出訊息的邏輯，例如將訊息添加到對話框中
        var chatMessage = document.createElement("div");
        chatMessage.textContent = "您：" + message;
        document.querySelector(".chat-box").appendChild(chatMessage);

        // 清空輸入框
        document.getElementById("message-input").value = "";
    }
});

window.addEventListener("scroll", function() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("back-to-top-btn").style.display = "block";
    } else {
        document.getElementById("back-to-top-btn").style.display = "none";
    }
});

function openChatBox() {
    document.getElementById("chat-box").style.display = "block";
}

function closeChatBox() {
    document.getElementById("chat-box").style.display = "none";
}

document.getElementById("industry-select").addEventListener("change", function() {
    filterTable();
});

function filterTable() {
    var select = document.getElementById("industry-select");
    var selectedValue = select.value;
    var table = document.getElementById("table-body");
    var rows = table.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var industryCell = rows[i].getElementsByTagName("td")[3];
        if (industryCell) {
            var industry = industryCell.textContent || industryCell.innerText;
            if (industry === selectedValue || selectedValue === "請選擇欲搜尋的產業類別") {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
}
  // 在這裡執行程式碼
});
