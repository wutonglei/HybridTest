var imgDom;
	$(function() {

		/*点击事件*/
		$("img").on("click", function() {
			console.log("点击调android拍照");
			imgDom = this; //将当前点击的img标签赋给变量imgDom
			console.log("当前节点:" + imgDom);
			window.android.takePhoto();

		});

	});

	/*显示图片*/
	function displayImg(path) {
		console.log("显示图片");
		$(imgDom).attr("src", "file://" + path);

	}