(function () {
    var ajaxForm_list = $('form.js-ajax-layer-form');
    if (ajaxForm_list.length) {
        var $btn;
        $('button.js-ajax-submit').on('click', function (e) {
            var btn = $(this);
            var form = btn.parents('form.js-ajax-layer-form');
            $btn=btn;

            if(btn.data("loading")){
                return;
            }
            //ie处理placeholder提交问题
            if ($.browser && $.browser.msie) {
                form.find('[placeholder]').each(function () {
                    var input = $(this);
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });
            }
        });

        ajaxForm_list.each(function(){
            $(this).validate({
                debug:true,
                //是否在获取焦点时验证
                //onfocusout : false,
                //当鼠标掉级时验证
                //onclick : false,
                //给未通过验证的元素加效果,闪烁等
                //highlight : false,
                onkeyup : function( element, event ){
                  return;
                },
                showErrors:function(errorMap, errorArr){
                    if(parseInt(errorArr.length) > 0){
                        $(errorArr[0].element).focus();
                        layer.msg(errorArr[0].message, {icon: 2});
                    }
                },
                submitHandler:function(form){
                    var $form= $(form);
                    $form.ajaxSubmit({
                        url: $btn.data('action') ? $btn.data('action') : $form.attr('action'),
                        dataType: 'json',
                        beforeSubmit: function (arr, $form, options) {
                            $btn.data("loading",true);
                            var text = $btn.text();
                            $btn.text(text + '中...').prop('disabled', true).addClass('disabled');
                        },
                        success: function (data, statusText, xhr, $form) {
                            var text = $btn.text();
                            $btn.removeClass('disabled').prop('disabled', false).text(text.replace('中...', '')).parent().find('span').remove();
                            if (data.state === 'success') {
                                layer.msg(data.msg, {icon: 1}, function () {
                                	if(data.result==1){
                                		layerModel.closeParent();
                                		parent.reloadCurrentGrid(1);
                                	}else if(data.result==2){
                                		layerModel.closeParent();
                                     	parent.reloadCurrentGrid(2);
                                	}else{
                                		 if (data.referer) {
                                         	 layerModel.closeParent();
                                         	 operaModel.redirect(data.referer);//返回带跳转地址
                                         } else {
                                             if (data.state === 'success') {
                                                 operaModel.reloadPage(window);//刷新当前页
                                             }
                                         }
                                	}
                                   
                                });
                            }else if(data.state === 'error'){
                                layer.msg(data.msg, {icon: 2});
                            }
                        },
                        error:function(xhr,e,statusText){
                            console.log(statusText);
                            operaModel.reloadPage(window);//刷新当前页
                        },
                        complete: function(){
                            $btn.data("loading",false);
                        }
                    });
                }
            });
        });
    }
})();