/**
 * OrderDetails
 * @Jayson Wang
 * 
 */
$(document).ready(
		function() {
			$("#table_page").bootstrapTable({
						cache : false,
						striped : true, // 是否显示行间隔色
						sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
						height : 600,
						pagination : true,
						minimumCountColumns : 2,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pageSize : 10, // 每页的记录行数（*）
						// pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
						uniqueId : "c3", // 每一行的唯一标识，一般为主键列
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
							fileName : 'OrderDetails', // 文件名称设置
							worksheetName : 'sheet1', // 表格工作区名称
							tableName : 'OrderDetails',
							excelstyles : [ 'background-color', 'color',
									'font-size', 'font-weight' ],
						// onMsoNumberFormat: DoOnMsoNumberFormat
						},

						paginationDetailHAlign: 'left',
						
						columns : [ {
							field : 'c1',
							title : 'Country',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c2',
							title : 'Area',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c3',
							title : 'OrderCode',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c4',
							title : 'Place  Time',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c5',
							title : 'Finished  Time',
							align : 'left',
							halign: 'center',
							halign: 'center'
						}, {
							field : 'c6',
							title : 'Product  Name',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c7',
							title : 'Quantity',
							align : 'right',
							halign: 'center'
						}, {
							field : 'c8',
							title : 'Order Amount',
							align : 'right',
							halign: 'center'
						}, {
							field : 'c9',
							title : 'Customer Company',
							align : 'center',
							halign: 'center'
						}, {
							field : 'c10',
							title : 'Contacter',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c11',
							title : 'Phone',
							align : 'left',
							halign: 'center'
							
						}, {
							field : 'c12',
							title : 'Contact Address',
							align : 'left',
							halign: 'center'
						}, {
							field : 'c13',
							title : 'Salesman',
							align : 'left',
							halign: 'center'
						},{
							field : 'c14',
							title : 'Supplier',
							align : 'left',
							halign: 'center'
						},

						]
					});

		});