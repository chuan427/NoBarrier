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

// 新增文章按鈕==>成功&失敗

function submitPost() {
	// 獲取文章標題和內容的值
	var title = document.getElementById('threadTitle').value.trim();
	var content = document.getElementById('description').value.trim();

	// 檢查文章標題和內容是否為空
	if (!title || !content) {
		alert("新增文章錯誤，標題或內容請勿空白，謝謝!");
		return; // 阻止表單提交
	}

	var formData = new FormData(document.querySelector('form'));
	fetch('/forum/insert', { // 確保 URL 與後端處理的 URL 一致
		method: 'POST',
		body: formData,
	})
		.then(response => {
			if (response.redirected) {
				window.location.href = response.url; // 如果後端發生重定向，則直接導航到重定向的 URL
			} else {
				// 假設後端響應是 JSON 格式，根據實際情況調整
				return response.json();
			}
		})
		.then(data => {
			// 可以根據後端返回的數據進行相應操作，這裡假設成功後跳出提示
			alert("新增文章成功");
		})
		.catch(error => console.error('錯誤:', error));
}

// 論壇刪除文章

function deletePostAndRedirect(postId) {
	if (confirm('確定要刪除這篇文章嗎？')) {
		$.ajax({
			type: 'POST',
			url: '/forum/delete',
			data: { fpNum: postId },
			success: function(response) {
				// 刪除成功後，重新導向到 /forum/forumIndex
				window.location.href = '/forum/forumIndex';
			},
			error: function(xhr, status, error) {
				// 處理錯誤
				alert('刪除失敗，請再試一次。');
			}
		});
	}
}

//重置留言

function resetForm() {
	document.querySelector('.comment-input').value = '';
}


//修改文章

function validateContent() {
    var title = document.getElementById('threadTitle').value.trim();
    var content = document.getElementById('description').value.trim();

    if (title === '') {
        alert('文章標題不能為空！');
        return false;
    }
    if (content === '') {
        alert('文章內容不能為空！');
        return false;
    }
    if (content.length < 20) {
        alert('文章內容不能少於 20 個字！');
        return false;
    }
    // 如果驗證通過，提交表單
    document.getElementById('updateForm').submit();
}