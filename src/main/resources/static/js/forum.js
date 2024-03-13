document.getElementById('fontSizeSelect').addEventListener('change', function() {
    document.getElementById('description').style.fontSize = this.value;
});

document.getElementById('fontTypeSelect').addEventListener('change', function() {
    document.getElementById('description').style.fontFamily = this.value;
});

$('#iconSelect').change(function() {
  var icon = $(this).val();
  var description = $('#description');
  description.val(description.val() + ' ' + icon); // 將圖標添加到 textarea 內容中
  $(this).val(''); // 重置下拉選單
});

document.getElementById('iconSelect').addEventListener('change', function() {
    var icon = this.value;
    if (icon) {
      document.getElementById('description').value += ` ${icon}`; // 在文本內容中插入選擇的圖示
      this.selectedIndex = 0; // 重置選擇框回到初始提示選項
    }
  });

  function hideContent(d) {
     document.getElementById(d).style.display = "none";
}



//預覽圖片
document.getElementById('fpImage').addEventListener('change', function(event) {
    var file = event.target.files[0];
    var imagePreview = document.getElementById('imagePreview');
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            var img = document.createElement('img');
            img.src = e.target.result;
            img.width = 100; // 設定圖片預覽的寬度
            img.height = 100; // 設定圖片預覽的高度

            // 清空預覽容器並新增預覽圖片
            imagePreview.innerHTML = '';
            imagePreview.appendChild(img);
        };
        reader.readAsDataURL(file);
    }
});


//三小點彈出視窗

function toggleMenu(clickedElement) {
    // 從被點擊的元素開始，向上尋找最近的包含`post-header`類的祖先元素
    var postHeader = clickedElement.closest('.post-header');
    // 在找到的`post-header`內尋找`menu`類的元素
    var menu = postHeader.querySelector('.menu');
    // 切換菜單的顯示狀態
    if (menu.style.display === "none") {
        menu.style.display = "block";
    } else {
        menu.style.display = "none";
    }
}
