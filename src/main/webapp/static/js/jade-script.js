/** 返回顶部按钮效果 */
$(window).scroll(function() {
	if ($(window).scrollTop() > 100) {
		$("#form_top").fadeIn(500);
	} else {
		$("#form_top").fadeOut(500);
	}
});
$("#form_top").click(// 定义返回顶部点击向上滚动的动画
function() {
	$('body').animate({
		scrollTop : 0
	}, 200);
});

/** 打印分页函数 */
function printPage(curPage, totalPage, url) {
	var pageHtml = [ '<nav><ul style="float:right;" class="pagination pagination-lg" id="'
			+ url + '">' ];
	/* 第一个 */
	if (curPage == 1) {
		var str = '<li class="disabled"><a href="#">&laquo;</a></li>';
		pageHtml.push(str);
	} else {
		var str = '<li><a href="#"  name="left">&laquo;</a></li>';
		pageHtml.push(str);
	}

	/* 中间 */
	if (totalPage <= 5) {
		for (var i = 1; i <= totalPage; i++) {
			if (i == curPage) {
				var str = '<li class="active"><a href="#">' + i + '</a></li>';
				pageHtml.push(str);
			} else {
				var str = '<li><a href="#">' + i + '</a></li>';
				pageHtml.push(str);
			}

		}
	} else {
		if (curPage < 5) {
			for (var i = 1; i <= 5; i++) {
				if (i == curPage) {
					var str = '<li class="active"><a href="#">' + i
							+ '</a></li>';
					pageHtml.push(str);
				} else {
					var str = '<li><a href="#">' + i + '</a></li>';
					pageHtml.push(str);
				}

			}
		} else if (curPage >= 5 && curPage < totalPage) {
			for (var i = 1; i <= 3; i++) {
				if (i != 3) {
					var str = '<li><a href="#">' + i + '</a></li>';
					pageHtml.push(str);
				} else {
					var str = '<li class="disabled"><a href="#">' + '...'
							+ '</a></li>';
					pageHtml.push(str);
				}
			}
			for (var i = curPage; i <= curPage + 1; i++) {
				if (i == curPage) {
					var str = '<li class="active"><a href="#">' + i
							+ '</a></li>';
					pageHtml.push(str);
				} else {
					var str = '<li><a href="#">' + i + '</a></li>';
					pageHtml.push(str);
				}

			}
		} else {
			for (var i = 1; i <= 3; i++) {
				if (i != 3) {
					var str = '<li><a href="#">' + i + '</a></li>';
					pageHtml.push(str);
				} else {
					var str = '<li class="disabled"><a href="#">' + '...'
							+ '</a></li>';
					pageHtml.push(str);
				}
			}
			for (var i = curPage - 1; i <= curPage; i++) {
				if (i == curPage) {
					var str = '<li class="active"><a href="#">' + i
							+ '</a></li>';
					pageHtml.push(str);
				} else {
					var str = '<li><a href="#">' + i + '</a></li>';
					pageHtml.push(str);
				}
			}
		}
	}

	/* 最后一个 */
	if (curPage == totalPage) {
		var str = '<li class="disabled"><a href="#">&raquo;</a></li>';
		pageHtml.push(str);
	} else {
		var str = '<li><a href="#"  name="right">&raquo;</a></li>';
		pageHtml.push(str);
	}
	pageHtml.push('</ul></nav>');
	pageHtml = pageHtml.join(" ");
	$("#printPage").html(pageHtml);

	var pageHtml2 = [ '<form class="form-inline" style="margin:30px 0px auto;float:left">' ];
	pageHtml2.push('共 ' + totalPage + " 页, 到第");
	pageHtml2
			.push('<input type="number" class="form-control" style="width:60px;" id="curPageInput">');
	pageHtml2.push('页 ');
	pageHtml2
			.push('<input type="button"class="btn btn-primary" id="curPageBtn" value="确定" />');
	pageHtml2.push('</form>');
	pageHtml2 = pageHtml2.join(" ");
	$("#printPage_f").html(pageHtml2);
}

/** 打印表格函数 */
function printTable(columnNames, data) {
	var table = [ '<table class="table table-hover table-striped table-bordered amber-font-20">' ];
	// Print t标题
	table.push('<tr>');
	table.push('<th>' + "#序号" + '</th>');
	for ( var k in columnNames) {
		table.push('<th>' + columnNames[k] + '</th>');
	}
	table.push('</tr>');
	// Print data
	var len = data.length;
	for (var i = 0; i < len; i++) {
		table.push('<tr>');
		table.push('<td>' + (i + 1) + '</td>');
		for ( var k in columnNames) {
			table.push('<td>' + data[i][k] + '</td>');
		}
		table.push('</tr>');
	}

	table.push('</table>');
	var table2 = table.join(" ");
	$("#printTable").html(table2);
}
function printTable2(columnNames, data) { //for index2.html
	var table = [ '<table class="table table-hover table-striped table-bordered amber-font-20">' ];
	// Print t标题
	table.push('<tr>');
	table.push('<th>' + "#序号" + '</th>');
	table.push('<th>' + "服务编号" + '</th>');
	table.push('<th colspan=3>' + "响应时间(Ex,En,He) ms" + '</th>');
	table.push('<th>' + "可靠性" + '</th>');
	table.push('</tr>');
	// Print data
	var len = data.length;
	for (var i = 0; i < len; i++) {
		table.push('<tr>');
		table.push('<td>' + (i + 1) + '</td>');
		for ( var k in columnNames) {
			table.push('<td>' + data[i][k] + '</td>');
		}
		table.push('</tr>');
	}

	table.push('</table>');
	var table2 = table.join(" ");
	$("#printTable").html(table2);
}

/** 分页按钮事件 */
function pageRecursion(totalPage, url) {
	/** 分页按钮事件 id=url1 */
	$("#url1 a").click(function() {
		var atext = parseInt($(this).text());
		if (atext >= 1 && atext <= totalPage) {
			curPage = atext;
			// 发送请求
			if (url == "1") {
				ask1();
			} else {
				ask2();
			}
		} else {
			var name = $(this).attr("name");
			if (name == "left") {
				curPage = curPage - 1;
				// 发送请求
				if (url == "1") {
					ask1();
				} else {
					ask2();
				}
			} else {
				curPage = curPage + 1;
				// 发送请求
				if (url == "1") {
					ask1();
				} else {
					ask2();
				}
			}
		}
	});
	$("#curPageBtn").click(function() {
		var curPageInput = parseInt($("#curPageInput").val());
		curPage = curPageInput;
		// 发送请求
		if (url == "1") {
			ask1();
		} else {
			ask2();
		}
	});
}

/** for index.html */
/** 按钮一的请求函数 */
function ask1() {
	// 发送请求
	$.getJSON(url1, {
		serverId : id,
		pageCount : pCountName,
		curPage : curPage
	}, function(data) {
		var array = data.list;
		printTable(columnNames, array);
		printPage(curPage, data.totalPage, "url1");
		pageRecursion(data.totalPage, 1);
	});
}
/** 按钮二的请求函数 */
function ask2() {
	// 发送请求
	$.getJSON(url2, {
		userIp : ip,
		pageCount : pCountName,
		curPage : curPage
	}, function(data) {
		var array = data.list;
		printTable(columnNames2, array);
		printPage(curPage, data.totalPage, "url1");
		pageRecursion(data.totalPage, 2);
	});
}
