function setTitle(title) {
	$("#title").html(title);
}

function setContent(content) {
	$("#content").html(content);
}

function setFrom(from) {
	$("#info #from").html(from);
}

function setTime(time) {
	$("#info #time").html(time);
}

function setCommentCount(countText) {
	$("#info #comment_count").html(countText);
}

function setEmpty() {
    setTitle("");
    setContent("");
    setFrom("");
    setTime("");
    setCommentCount("");
}