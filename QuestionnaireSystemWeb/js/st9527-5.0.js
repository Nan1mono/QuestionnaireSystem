// 自执行函数。  基于jQuery，所以一定要先引入jQuery，再引入我们的js
(function createSt9527(w){
    // w就是window对象
    // 给window添加一个属性 st9527,这个属性本身是一个Object对象。
    w.st9527=new Object();
    // 给st9527这个对象添加一个方法table  ,  table方法的参数是 
    //tableId 要渲染的表格的id
    // columns 表格的列 
        // [
        //     {field:'st9527-ck'},
        //     {field:'topicId',title:'帖子编号'},
        //     {field:'topicTitle',title:'帖子标题'},
        //     {field:'created',title:'创建时间'},
        //     {field:'option',title:'操作'}
        // ]
    //url  请求数据的url
    //params  请求的时候携带的参数
    //pageBarId 分页栏的组建id
    st9527.table=function(tableId,columns,url,params,pageBarId,callback){
        //根据ur渲染表格数据
        // 获取表格本身
        var _table  = $("#"+tableId);
        if(!_table){
            return;//如果表格不存在直接结束。
        }
        console.log(params);
        //请求数据
        $.ajax({url:url,type:"GET",data:params?params:{},success:function(data){
            // 记录最后一列的操作栏
            var optionBar = undefined;

            //将数据直接显示在表格中。
            //表格有没有表头。
            // 表格中原来有没有数据。
            // 将表格清空
            _table.empty();
            //创建一个tbody
            var _tbody = $("<tbody>");
            _table.append(_tbody);
            _table = _tbody;
            //添加表头 
            if(columns){
                let tr = "<tr>";
                for(var x in columns){
                    //注意读取操作栏
                    var col = columns[x];
                    //判断是否是复选框
                    if(col.field=='st9527-ck'){
                        //复选框
                        tr+="<th><input type='checkbox' /></th>";
                    }else {
                        if(col.field=='st9527-options'){
                            //找到菜单栏，并且记录
                            optionBar = $("#"+col.barId).clone();
                            // 将原来的影藏
                            $("#"+col.barId).css("display","none");
                        }
                        var title = col.title;
                        tr+="<th>"+title+"</th>"
                    }   
                }
                tr+="</tr>";
                _table.append(tr);
            }
            // 将加载的数据显示到表格中。
            if(data.code==200){
                //取出数据
                let list = data.data;
                if(data.obj==0){
                    let tr = "<tr> <td colspan='"+columns.length+"' style='text-align:center;'>暂时没有查询到数据</td></tr>";
                    _table.append(tr);
                }
                //遍历显示
                for(let x = 0;x < list.length;x ++){
                    let row = list[x];
                    let tr = $("<tr>");
                    for(let i = 0;i < columns.length;i++){
                        let col = columns[i];
                        var idFiled = columns[0].field=='st9527-ck'?columns[1].field:columns[0].field;
                        //判断是否是复选框
                        if(col.field == 'st9527-ck'){//默认是第一列
                            tr.append($("<td><input type='checkbox' value='"+row[idFiled]+"' /></td>"));
                        }else{
                            // 判断是否是操作的列
                            if(col.field=='st9527-options'){
                                //如果是操作列
                                // 先克隆
                                let nowOpBar = optionBar.clone();
                                nowOpBar.css("display","block")
                                // 再设置id  第一列
                                nowOpBar.attr("dataId",row[idFiled]);
                                // 将整个对象转换为json设置为div的属性data的值
                                nowOpBar.attr("data",JSON.stringify(row));
                                //将操作栏加入到td中
                                let td = $("<td>");
                                td.append(nowOpBar);
                                tr.append(td);
                            }else if(col.formatter){// 判断是否有格式化函数
                                tr.append($("<td>"+col.formatter(row[col.field])+"</td>"));
                            }else{
                                tr.append($("<td>"+row[col.field]+"</td>"));
                            }
                        }
                    }
                    _table.append(tr);
                }
            }else{
                //如果失败， 就添加一行，只显示错误信息即可
                let tr = "<tr> <td colspan='"+columns.length+"' style='text-align:center;'>"+data.msg+"</td></tr>";
                _table.append(tr);
            }
            //给全选按钮添加事件
            var cks = _table.find("[type=checkbox]");
            if(cks.length > 0){//有复选框
                //取出第一个添加事件
                $(cks[0]).bind('click',function(){
                    cks.prop('checked',$(cks[0]).prop("checked"));
                });
            }

            //处理分页栏。
            if(pageBarId){
                //当前页码
                let page = params.page?params.page:1;
                let size = params.size?params.size:10;
                console.log(data.obj);
                //计算最大页码
                let maxPageNum = parseInt(Math.ceil(eval(data.obj+"") / size));
                console.log(maxPageNum);
                var pageBar = $("#"+pageBarId);
                pageBar.empty();
                pageBar.css("text-align","center");
                var ul = $("<ul>");
                ul.addClass("pagination");
                ul.append("<li start='1'><a href='javascript:void(0)' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
                if(maxPageNum<10){
                    for(let i = 0;i < maxPageNum ;i++){
                        if((i+1)==page){
                            ul.append("<li class='active'><a href='javascript:void(0)'>"+(i+1)+"</a></li>")
                        }else{
                            ul.append("<li><a href='javascript:void(0)'>"+(i+1)+"</a></li>")
                        }
                    }
                }else{
                    let i = (page - 4) <= 0 ? 1 : (page-4)
                    var end = i + 9;
                    for(;i <= end && i <= maxPageNum ;i++){
                        if((i)==page){
                            ul.append("<li class='active'><a href='javascript:void(0)'>"+i+"</a></li>")
                        }else{
                            ul.append("<li><a href='javascript:void(0)'>"+i+"</a></li>")
                        }
                    }
                }
                ul.append("<li end='1'><a href='javascript:void(0)' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a> </li>");
                //设置样式
                // ul.find("li").css({    "list-style": "none",
                // "display": "inline-block",
                // "width": "25px",
                // "height": "25px",
                // "margin": "1px",
                // "border": "1px solid #ccc",
                // "font-size": "14px",
                // "text-align": "center",
                // "line-height": "25px",
                // "cursor":"pointer"});
                pageBar.append(ul);
                //给所有的li添加单击事件
                ul.find("li").bind('click',function(){
                    var text = $(this).text();
                    var newPage = text;// 目标页码
                    if(newPage==page){
                        return;//如果目标页面就是当前页码，不做任何处理。
                    }
                    if($(this).attr('start')==1){
                        newPage = page - 1 > 0?page - 1:1;
                    }else if($(this).attr('end')==1){
                        newPage = page + 1 > maxPageNum?maxPageNum:page + 1;
                    }
                    //得到最新的目标页码(将渲染表格的函数再次执行一次)
                    //修改params中的页码
                    params.page = newPage;
                    st9527.table(tableId,columns,url,params,pageBarId,callback);
                });
            }
            //调用回调函数
            callback?callback():"";
        },
    async:false});
    }
})(window);