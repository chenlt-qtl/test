var Util = {}; 
Util.doAjaxPost =function(targetUrl, options, callback) {
	$.ajax({
		// jquery 1.4
		traditional : true,
		type : "POST",
		url : targetUrl,
		data : options,
		dataType : "text",
		cache : false,
		success : function(data) {
			callback(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			Message.errorAlert("Invoke ajax error:" + textStatus + " " + errorThrown);
		}
	});
};

$.fn.serializeObject=function(){
	var json = {}
	var fields = $(this).serializeArray();
	jQuery.each(fields, function(i, field) {
		json[field.name] =  $.trim(field.value);
	});
	return json;
}

window.Message = window.Message || {
	successAlert : function(msg,callback) {
		BUI.Message.Alert(msg, callback,'success');
	},

	errorAlert : function(msg,callback) {
		BUI.Message.Alert(msg,callback, 'error');
	},
	confirm : function(msg, callback) {
		BUI.Message.Confirm(msg, callback, 'question');
	}
};


