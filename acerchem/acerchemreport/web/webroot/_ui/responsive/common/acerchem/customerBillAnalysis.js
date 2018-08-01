/**
 * Bill Analysis
 * @Jayson Wang
 */
$(document).ready(
		function() {

			$("#table_page1").bootstrapTable(
					{
						// method:'POST',
						// dataType:'json',
						// contentType: "application/x-www-form-urlencoded",
						toolbar : '#toolbar1', // 工具按钮用哪个容器
						cache : false,
						striped : true, // 是否显示行间隔色

						sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
						// url:url,
						// height : $(window).height() - 200,
						height : 600,
						// width : $(window).width(),
						// showColumns:true,
						pagination : true,
						// queryParams : queryParams,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 10, // 每页的记录行数（*）
						pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
						uniqueId : "c1", // 每一行的唯一标识，一般为主键列
						sortable : true,

						showExport : true, // 是否显示导出按钮
						exportDataType:"all",
						buttonsAlign : "right", // 按钮位置
						exportTypes : [ 'excel' ], // 导出文件类型
						// toolbar: "#exampleTableEventsToolbar",
						icons : {
							refresh : "glyphicon-repeat",
							toggle : "glyphicon-list-alt",
							columns : "glyphicon-list"
						},
						exportOptions : {
							// ignoreColumn: [0,1], //忽略某一列的索引
							fileName : 'CustomerBillAnalysis', // 文件名称设置
							worksheetName : 'sheet1', // 表格工作区名称
							tableName : 'Customer Bill Analysis Report',
							excelstyles : [ 'background-color', 'color',
									'font-size', 'font-weight' ],
						// onMsoNumberFormat: DoOnMsoNumberFormat
						},

						columns : [ {
							field : 'c1',
							title : 'Order Code'
						}, {
							field : 'c2',
							title : 'Customer'
						}, {
							field : 'c3',
							title : 'Employee'
						}, {
							field : 'c4',
							title : 'Place Time'
						}, {
							field : 'c5',
							title : 'Finished TIme'
						},

						{
							field : 'c6',
							title : 'Prepaid'
						}, {
							field : 'c7',
							title : 'Bill Period'
						}, {
							field : 'c8',
							title : '0-30'
						}, {
							field : 'c9',
							title : '30-60'
						}, {
							field : 'c10',
							title : '60-90'
						}, {
							field : 'c11',
							title : '>90'
						},

						]
					});

			$("#table_page2").bootstrapTable(
					{

						toolbar : '#toolbar2', // 工具按钮用哪个容器
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
						height : 600,
						width : 300,
						pagination : true,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 10, // 每页的记录行数（*）
						// pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
						uniqueId : "c1", // 每一行的唯一标识，一般为主键列
						sortable : true,

						
						
						showExport : true, // 是否显示导出按钮
						buttonsAlign : "right", // 按钮位置
						exportDataType:"all",
						exportTypes : [ 'excel' ], // 导出文件类型
						// toolbar: "#exampleTableEventsToolbar",
						icons : {
							refresh : "glyphicon-repeat",
							toggle : "glyphicon-list-alt",
							columns : "glyphicon-list"
						},
						exportOptions : {
							// ignoreColumn: [0,1], //忽略某一列的索引
							fileName : 'CreditAccountAnalysis', // 文件名称设置
							worksheetName : 'sheet1', // 表格工作区名称
							tableName : 'CreditAccount',
							excelstyles : [ 'background-color', 'color',
									'font-size', 'font-weight' ],
						// onMsoNumberFormat: DoOnMsoNumberFormat
						},
						

						columns : [ {
							field : 'c1',
							title : 'Customer'
						}, {
							field : 'c2',
							title : 'LineOfCredit'
						}, {
							field : 'c3',
							title : 'UsedCredit'
						}, {
							field : 'c4',
							title : 'ResidueCredit'
						},

						]
					});

		});