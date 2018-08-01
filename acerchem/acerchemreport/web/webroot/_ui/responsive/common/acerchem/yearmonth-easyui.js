
        $(function () {
                $('#birth_year').datebox({
                    onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                        span.trigger('click'); //触发click事件弹出月份层
                        if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                            tds = p.find('div.calendar-menu-month-inner td');
                            tds.click(function (e) {
                                e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                                var year = $(".calendar-menu-year").val();
                                var month = parseInt($(this).attr('abbr'), 10);
                                $('#birth_year').datebox('hidePanel')//隐藏日期对象
                                .datebox('setValue', year + '-' + month); //设置日期的值
                            });
                        }, 0)
                    },
                    parser: function (s) {//配置parser，返回选择的日期
                        if (!s) return new Date();
                        var ss = (s.split('-'));
                        var y = parseInt(ss[0],10);
                        var m = parseInt(ss[1],10);            
                        if (!isNaN(y) && !isNaN(m)){
                            return new Date(y,m-1);
                        } else {
                            return new Date();
                        }  
                    },
                    formatter: function (d) { 
                        debugger;
                        var y = d.getFullYear();
                        var m = d.getMonth()+1;
                        var d = d.getDate();
                        return y+'-'+(m<10?('0'+m):m);
                    }//配置formatter，只返回年月
                });
                var p = $('#birth_year').datebox('panel'), //日期选择对象
                    tds = false, //日期选择对象中月份
                    span = p.find('span.calendar-text'); //显示月份层的触发控件
            });

