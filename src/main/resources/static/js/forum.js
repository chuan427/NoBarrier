document.getElementById('fontSizeSelect').addEventListener('change', function() {
    document.getElementById('description').style.fontSize = this.value;
});

document.getElementById('fontTypeSelect').addEventListener('change', function() {
    document.getElementById('description').style.fontFamily = this.value;
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

