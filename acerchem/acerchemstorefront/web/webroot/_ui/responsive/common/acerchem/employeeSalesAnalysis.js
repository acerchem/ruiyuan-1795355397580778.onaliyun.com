/**
 * Employee Sales Analysis
 * @Jayson Wang
 */
$(document).ready(
		function() {
			$("#table_page").bootstrapTable({
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
							fileName : 'EmployeeSalesAnalysis', // 文件名称设置
							worksheetName : 'sheet1', // 表格工作区名称
							tableName : 'EmployeeSales',
							excelstyles : [ 'background-color', 'color',
									'font-size', 'font-weight' ],
						// onMsoNumberFormat: DoOnMsoNumberFormat
						},

						columns : [ {
							field : 'c1',
							title : 'Month',
							halign: 'center'
						}, {
							field : 'c2',
							title : 'Employee',
							halign: 'center'
						}, {
							field : 'c3',
							title : 'Amount',
							halign: 'center'
						}, 

						]
					});

		});