$(document).ready(function() {
	// 关闭 [class="pop_container"]弹出页面
	$('.pop_container').on('click', '.pop_close_btn', function(e) {
		var $this = $(this);
		$this.parents('.pop_container').hide();
	});
	$('body').on('click', function(e) {
		var target = $(e.target);
		if (target.closest('.pop_container').length == 0
				&& $('.pop_container:visible').length > 0
				&& target.closest('[belong="mask"]').length == 0) {
			$('.pop_container').hide();
		}
	});

	// 关闭[class="modal_layer"]模态框
	$('body').on('click', '.modal_layer .modal_top .modal_close, .modal_layer .bg_cover', function(e) {
		var $this = $(this);
		$this.parents('.modal_layer').hide();
	});
});

// 使用方法：参考 WebContent/jspTest/DevelopAPI.jsp
$.extend({
	// 消息框
	message: {
		/*
		 * 创建
		 * msg: 消息div对象
		 */
		_create: function(msg) {
			var msgBox = $('.message_box');
			if (msgBox.length > 0) {
				msgBox.append(msg);
			} else {
				var newMsgBox = $('<div class="message_box" belong="mask"></div>');
				newMsgBox.append(msg);
				$('body').append(newMsgBox);
			}
			this._destroy(msg);
		},
		/*
		 * 销毁
		 * msg: 消息div对象
		 */
		_destroy: function(msg) {
			var msgBox = $('.message_box');
			msg.delay(3000).animate({opacity: 'hide'}, 500, function() {
				msg.remove();
				if (msgBox.children('.message').length == 0) {
					msgBox.remove();
				}
			});
		},
		/*
		 * 信息消息框
		 * content: 显示的文本内容
		 */
		info: function(content) {
			if (!content || content == null || content == ''){content = 'Info...';}
			var msg = $('<div class="message message_info">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 成功消息框
		 * content: 显示的文本内容
		 */
		success: function(content) {
			if (!content || content == null || content == ''){content = 'Success...';}
			var msg = $('<div class="message message_success">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 警告消息框
		 * content: 显示的文本内容
		 */
		warning: function(content) {
			if (!content || content == null || content == ''){content = 'Warning...';}
			var msg = $('<div class="message message_warning">' + content + '</div>');
			this._create(msg);
		},
		/*
		 * 错误消息框
		 * content: 显示的文本内容
		 */
		error: function(content) {
			if (!content || content == null || content == ''){content = 'Error...';}
			var msg = $('<div class="message message_error">' + content + '</div>');
			this._create(msg);
		}
	},
	// 加载框
	loading: {
		// 同时调用加载框的数量
		_loadCount: 0,
		/*
		 * 显示
		 * msg: 可选，显示的文本内容
		 * timer: 可选(传此参数时第1个参数必须传值或者null)，毫秒，指定时间后自动关闭加载框
		 */
		show: function(msg, timer) {
			this._loadCount++;
			if ($('.loading_layer').length > 0) {
				return;
			}
			if (!msg){msg = '';}
			var ldgLayer = $('<div class="loading_layer" belong="mask">'
							+ '<div class="bg_cover"></div>'
							+ '<div class="loading_box">'
							+ '<span class="loading_align_bar"></span><i class="loading_img"></i><span class="loading_msg">' + msg + '</span>'
							+ '</div>'
							+ '</div>');
			$('body').append(ldgLayer);
			var reg = /^\d+(?=\.{0,1}\d+$|$)/; // 正数
			if (timer && reg.test(timer)) {
				setTimeout(this.hide, timer);
			}
		},
		/*
		 * 销毁
		 */
		hide: function() {
			this._loadCount--;
			if (this._loadCount > 0) {
				return;
			} else {this._loadCount = 0;}
			$('.loading_layer').remove();
		}
	},
	// 对话框
	dialog: {
		// 层叠顺序z-index
		_zIdx: 3000,
		/*
		 * 提示框
		 * content: 可选，显示的文本内容
		 * title: 可选(传此参数时第1个参数必须传值或者null)，显示的标题内容
		 * cnfBtnText: 可选(传此参数时前2个参数必须传值或者null)，改变[确定]按钮的文本内容
		 * cnfCallback: 可选(传此参数时前3个参数必须传值或者null)，[确定]按钮的回调函数
		 * xCallback: 可选(传此参数时前4个参数必须传值或者null)，[X]按钮的回调函数
		 */
		alert: function(content, title, cnfBtnText, cnfCallback, xCallback) {
			if (!content){content = '';}
			if (!title){title = '提示';}
			if (!cnfBtnText){cnfBtnText = '确 定';}
			var dlgLayer = $('<div class="dialog_layer" belong="mask">'
						+ '<div class="bg_cover" style="z-index:' + this._zIdx++ + '"></div>'
						+ '<div class="dialog_box" style="z-index:' + this._zIdx++ + '">'
						+ '<div class="dialog_top"><span class="dialog_title">' + title + '</span><span class="dialog_close">x</span></div>'
						+ '<div class="dialog_middle"><span class="middle_align_bar"></span><span class="dialog_content">' + content + '</span></div>'
						+ '<div class="dialog_bottom"><div class="btn btn_primary" name="cfBtn">' + cnfBtnText + '</div></div>'
						+ '</div>'
						+ '</div>');
			$('body').append(dlgLayer);
			// [确定]按钮绑定点击事件
			dlgLayer.find('[name="cfBtn"]').bind('click', function(e) {
				if (cnfCallback && cnfCallback != null) {
					cnfCallback();
				}
				dlgLayer.remove();
			});
			// [X]按钮绑定点击事件
			dlgLayer.find('.dialog_close').bind('click', function(e) {
				if (xCallback && xCallback != null) {
					xCallback();
				}
				dlgLayer.remove();
			});
		},
		/*
		 * 确认框
		 * content: 可选，显示的文本内容
		 * title: 可选(传此参数时第1个参数必须传值或者null)，显示的标题内容
		 * cnfBtnText: 可选(传此参数时前2个参数必须传值或者null)，改变[确定]按钮的文本内容
		 * cncBtnText: 可选(传此参数时前3个参数必须传值或者null)，改变[取消]按钮的文本内容
		 * cnfCallback: 可选(传此参数时前4个参数必须传值或者null)，[确定]按钮的回调函数
		 * cncCallback: 可选(传此参数时前5个参数必须传值或者null)，[取消]按钮的回调函数
		 * xCallback: 可选(传此参数时前6个参数必须传值或者null)，[X]按钮的回调函数
		 */
		confirm: function(content, title, cnfBtnText, cncBtnText, cnfCallback, cncCallback, xCallback, interval) {
			if (!content){content = '';}
			if (!title){title = '提示';}
			if (!cnfBtnText){cnfBtnText = '确 定';}
			if (!cncBtnText){cncBtnText = '取 消';}
			var dlgLayer = $('<div class="dialog_layer" belong="mask">'
						+ '<div class="bg_cover" style="z-index:' + this._zIdx++ + '"></div>'
						+ '<div class="dialog_box" style="z-index:' + this._zIdx++ + '">'
						+ '<div class="dialog_top"><span class="dialog_title">' + title + '</span><span class="dialog_close">x</span></div>'
						+ '<div class="dialog_middle"><span class="middle_align_bar"></span><span class="dialog_content">' + content + '</span></div>'
						+ '<div class="dialog_bottom"><div class="btn btn_primary" name="cfBtn">' + cnfBtnText + '</div><div class="btn btn_default" name="ccBtn">' + cncBtnText + '</div></div>'
						+ '</div>'
						+ '</div>');
			$('body').append(dlgLayer);
			// [确定]按钮绑定点击事件
			dlgLayer.find('[name="cfBtn"]').bind('click', function(e) {
				if (cnfCallback && cnfCallback != null) {
					cnfCallback();
				}
				dlgLayer.remove();
			});
			// [取消]按钮绑定点击事件
			dlgLayer.find('[name="ccBtn"]').bind('click', function(e) {
				if (cncCallback && cncCallback != null) {
					cncCallback();
				}
				dlgLayer.remove();
			});
			// [X]按钮绑定点击事件
			dlgLayer.find('.dialog_close').bind('click', function(e) {
				if (xCallback && xCallback != null) {
					xCallback();
				}
				dlgLayer.remove();
			});
			//interval不为空时模拟点击[确定]按钮绑定点击事件
			if (interval && interval != null) {
				setTimeout(function(){
					if(cnfCallback && cnfCallback != null){
						cnfCallback();
					}
					dlgLayer.remove();
				}, interval*1000);
			}
		}
	},
	// 模态框
	modal: function(id, option) {
		var $ele = $(id);
		if ($ele.length == 0) {
			$.message.error('没有找到DOM对象：' + id);
			return;
		}
		if (option && option != null) {
			if (option.width && $UTIL.dataType.isNumber(option.width)) {
				$ele.find('.modal_box').css('width', option.width + 'px');
				$ele.find('.modal_box').css('margin-left', '-' + (option.width / 2) + 'px');
			}
			if (option.height && $UTIL.dataType.isNumber(option.height)) {
				$ele.find('.modal_box').css('height', option.height + 'px');
				$ele.find('.modal_box').css('margin-top', '-' + (option.height / 2) + 'px');
			}
		}
	},
	//停止冒泡
	stopPropagation: function(e) {
		window.event? window.event.cancelBubble = true : e.stopPropagation();
	},
	// 阻止默认行为
	preventDefault: function(e) {
		window.event? window.event.returnValue = false : e.preventDefault();
	}
});

var $UTIL = {
	// 判断浏览器
	browser: {
		isIE: (/(msie\s|trident.*rv:)([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isFirefox: (/(firefox)\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isOpera: (/(opera).+version\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isChrome: (/(chrome)\/([\w.]+)/.exec(navigator.userAgent.toLowerCase()) ? true : false),
		isSafari: (/version\/([\w.]+).*(safari)/.exec(navigator.userAgent.toLowerCase()) ? true : false)
	},
	// 判断数据类型
	dataType: {
		_type: Object.prototype.toString,
		isString: function(o) {
			return this._type.call(o) == '[object String]';
		},
		isNumber: function(o) {
			return this._type.call(o) == '[object Number]';
		},
		isBoolean: function(o) {
			return this._type.call(o) == '[object Boolean]';
		},
		isUndefined: function(o) {
			return this._type.call(o) == '[object Undefined]';
		},
		isNull: function(o) {
			return this._type.call(o) == '[object Null]';
		},
		isObject: function(o) {
			return this._type.call(o) == '[object Object]';
		},
		isArray: function(o) {
			return this._type.call(o) == '[object Array]';
		},
		isFunction: function(o) {
			return this._type.call(o) == '[object Function]';
		},
		test: function() {
			var obj = 'text';
			console.log('[String: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = 123;
			console.log('[Number: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = true;
			console.log('[Boolean: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = undefined;
			console.log('[Undefined: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isobject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = null;
			console.log('[Null: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = {key:'value'};
			console.log('[Object: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = [11, 22, 33];
			console.log('[Array: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
			obj = function(){};
			console.log('[Function: ' + obj + '] >>   isString:' + this.isString(obj) + ', isNumber:' + this.isNumber(obj) + ', isBoolean:' + this.isBoolean(obj) + ', isUndefined:' + this.isUndefined(obj) + ', isNull:' + this.isNull(obj) + ', isObject:' + this.isObject(obj) + ', isArray:' + this.isArray(obj) + ', isFunction:' + this.isFunction(obj));
		}
	},
	// 时间工具
	timeTool: {
		// 获取时间差（格式：xx天 xx:xx:xx）
		getTimeDiff: function(start, end) {
			var time = '';
			var diff = parseInt((end.getTime() - start.getTime()) / 1000); // 相差x秒
			if (diff < 0) {
				diff = -1 * diff;
			}
			var day = parseInt(diff / (24 * 60 * 60)); // 天数
			time += day + '天 ';
			var hour = parseInt((diff % (24 * 60 * 60)) / (60 * 60)); // 时
			if (hour < 10) {
				time += '0' + hour + ':';
			} else {
				time += hour + ':';
			}
			var minute = parseInt((diff % (60 * 60)) / (60)); // 分
			if (minute < 10) {
				time += '0' + minute + ':';
			} else {
				time += minute + ':';
			}
			var second = parseInt(diff % 60); // 秒
			if (second < 10) {
				time += '0' + second;
			} else {
				time += second;
			}
			return time;
		}
	}
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).format('yyyy-MM-dd hh:mm:ss.S') ==> 2006-07-02 08:09:04.423
//(new Date()).format('yyyy-M-d h:m:s.S')      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt) {
	var o = {
		'M+': this.getMonth() + 1, //月份
		'd+': this.getDate(), //日
		'h+': this.getHours(), //小时
		'm+': this.getMinutes(), //分
		's+': this.getSeconds(), //秒
		'q+': Math.floor((this.getMonth() + 3) / 3), //季度
		'S' : this.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(fmt)){fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));}
	for(var k in o)
		if(new RegExp('(' + k + ')').test(fmt)){fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));}
	return fmt;
}