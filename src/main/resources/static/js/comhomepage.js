document.addEventListener('DOMContentLoaded', function() {
  
 // 設定目標日期和時間（假設是未來的某個時間）
 const targetDate = new Date('2024-02-20T23:59:59').getTime();

 // 更新倒數計時器的函數
 function updateCountdown() {
     const currentDate = new Date().getTime();
     const difference = targetDate - currentDate;

     const days = Math.floor(difference / (1000 * 60 * 60 * 24));
     const hours = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
     const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
     const seconds = Math.floor((difference % (1000 * 60)) / 1000);

     document.getElementById('countdown').innerHTML = `剩餘時間：${days} 天 ${hours} 小時 ${minutes} 分鐘 ${seconds} 秒`;
 }

 // 每秒更新一次倒數計時器
 setInterval(updateCountdown, 1000);

 // 初始呼叫一次以避免延遲
 updateCountdown();

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
  // 在這裡執行程式碼
});
  