document.addEventListener('DOMContentLoaded', function() {


	const chatBox = document.getElementById('chat-box');
	const messageInput = document.getElementById('message-input');

	function sendMessage() {
		const message = messageInput.value.trim();
		if (message !== '') {
			appendMessage('You', message);
			messageInput.value = '';
			// Send message to server or other clients
		}
	}

	function appendMessage(sender, message) {
		const messageElement = document.createElement('div');
		messageElement.innerHTML = `<strong>${sender}:</strong> ${message}`;
		chatBox.appendChild(messageElement);
		chatBox.scrollTop = chatBox.scrollHeight;
	}

	// Simulate receiving a message
	appendMessage('User1', 'Hello!');
	appendMessage('User2', 'Hi there!');

	function sendImage() {
		const input = document.getElementById('image-input');
		const file = input.files[0];
		if (!file) {
			alert('Please select an image.');
			return;
		}

		// Assuming you have a function to handle image uploading
		uploadImage(file);
	}

	function uploadImage(file) {
		// Code to upload the image to your server goes here
		// You can use XMLHttpRequest, fetch API, or any other method to send the image data to your server
		// After uploading, you can display the image in the chat box or handle it as needed
		const image = document.createElement('img');
		image.src = URL.createObjectURL(file);
		image.classList.add('chat-image');
		document.getElementById('chat-box').appendChild(image);
	}
	// 在這裡執行程式碼
});

