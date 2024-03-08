//const itemsPerPage = 5; // 每頁顯示的數量
//let currentPage = 1; // 當前頁數
//const data = [
//    { id: '2024-02-17', name: '新產品發布', name1: '我們很高興宣布推出一款全新的工業機械零件，這款產品具有更高的耐用性和精密加工，可滿足客戶更高的要求。'},
//    { id: '2024-02-16', name: '參加行業展覽', name1: '我們將參加即將舉行的工業機械展覽，展示我們的最新產品和技術，歡迎各界朋友前來參觀交流。'},
//    { id: '2024-02-15', name: '公司擴張計劃', name1: '為了滿足市場需求，我們計劃擴大生產規模，提高產品供應能力，以更好地服務客戶。'},
//    { id: '2024-02-14', name: '獲得新認證', name1: '我們最近獲得了ISO9001質量管理認證，這表明我們的產品和服務符合國際標準，為客戶提供了更大的信心。'},
//    { id: '2024-02-13', name: '專題報導', name1: '介紹公司的特色產品、專案或成就，吸引更多關注。'},
//    { id: '2024-02-12', name: '客戶故事', name1: '分享客戶的成功故事或合作案例，展示公司的服務和價值。'},
//    { id: '2024-02-11', name: '公司擴張計劃', name1: '為了滿足市場需求，我們計劃擴大生產規模，提高產品供應能力，以更好地服務客戶。'},
//    { id: '2024-02-10', name: '行業動態', name1: '分享行業內的最新趨勢、技術發展等信息，提高公司在行業內的聲譽。'},
//    { id: '2024-02-09', name: '客戶故事', name1: '分享客戶的成功故事或合作案例，展示公司的服務和價值。'},
//    { id: '2024-02-08', name: '獲得新認證', name1: '我們最近獲得了ISO9001質量管理認證，這表明我們的產品和服務符合國際標準，為客戶提供了更大的信心。'},
//    { id: '2024-02-07', name: '公司活動通知', name1: '我們將於下周舉行員工聚餐活動，歡迎全體員工踴躍參加，共度美好時光。'},
//    { id: '2024-02-06', name: '參加行業展覽', name1: '我們將參加即將舉行的工業機械展覽，展示我們的最新產品和技術，歡迎各界朋友前來參觀交流。'}
//];

//function renderTable(page) {
//    const startIndex = (page - 1) * itemsPerPage;
//    const endIndex = startIndex + itemsPerPage;
//    const tableBody = document.getElementById('table-body');
//    tableBody.innerHTML = '';
//    for (let i = startIndex; i < endIndex && i < data.length; i++) {
//        const row = document.createElement('tr');
//        row.innerHTML = `<td>${data[i].id}</td><td>${data[i].name}</td><td>${data[i].name1}</td>`;
//        tableBody.appendChild(row);
//    }
//}
//
//function renderPagination() {
//    const totalPages = Math.ceil(data.length / itemsPerPage);
//    const pagination = document.getElementById('pagination').querySelector('.pagination');
//    pagination.innerHTML = '';
//    for (let i = 1; i <= totalPages; i++) {
//        const li = document.createElement('li');
//        li.className = 'page-item';
//        const a = document.createElement('a');
//        a.className = 'page-link';
//        a.innerText = i;
//        a.href = '#';
//        a.addEventListener('click', function() {
//            currentPage = i;
//            renderTable(currentPage);
//            highlightCurrentPage();
//            const table = document.getElementById('table-body');
//            table.scrollIntoView({ behavior: 'smooth', block: 'start' });
//        });
//        
//        li.appendChild(a);
//        pagination.appendChild(li);
//    }
//    highlightCurrentPage();
//}

//function highlightCurrentPage() {
//    const pagination = document.getElementById('pagination').querySelector('.pagination');
//    const pages = pagination.querySelectorAll('.page-link');
//    pages.forEach(page => {
//        if (parseInt(page.innerText) === currentPage) {
//            page.parentNode.classList.add('active');
//        } else {
//            page.parentNode.classList.remove('active');
//        }
//    });
//}
//
//renderTable(currentPage);
//renderPagination();
//
//document.getElementById("back-to-top-btn").addEventListener("click", function() {
//    window.scrollTo({
//        top: 0,
//        behavior: "smooth"
//    });
//});
//
window.addEventListener("scroll", function() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("back-to-top-btn").style.display = "block";
    } else {
        document.getElementById("back-to-top-btn").style.display = "none";
    }
});

//新加的 讓ul pagination下面的 a標籤 不要跳轉
//let paginationUlClass = document.querySelector(".pagination");
//let aTag = pagination.querySelectorAll("a");
//aTag.forEach(function(anchor){
//	anchor.addEventListener("click",function(e){
//		e.preventDefault();
//	})
//});
