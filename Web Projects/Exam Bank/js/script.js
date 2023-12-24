$(document).ready(function () {
	$('#practice').click(function (e) {
		e.preventDefault();
		$('#content').load('practice.html #practice-content');
	});

	$('#test').click(function (e) {
		e.preventDefault();
		$('#content').load('test.html');
	});
});
