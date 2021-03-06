tasksController = function() { 
	
	function errorLogger(errorCode, errorMessage) {
		console.log(errorCode +':'+ errorMessage);
	}
	
	var taskPage;
	var initialised = false;

    var sortCriteria = {
        'thPriority' : function (asc) {
			return function(task1, task2) {
                if (asc) {
                    return task1.priority < task2.priority;
                } else {
                    return task1.priority > task2.priority;
				}
			};
        },
        'thDue' : function (asc) {
			return function (task1, task2) {
				if (asc) {
					return Date.parse(task1.dueDate).compareTo(Date.parse(task2.dueDate));
				} else {
					return Date.parse(task2.dueDate).compareTo(Date.parse(task1.dueDate));
				}
			};
        },
        'thUserId' : function (asc) {
			return function (task1, task2) {
				if (asc) {
					return task1.userId < task2.userId;
				} else {
					return task1.userId > task2.userId;
				}
			};
        },
};

    /**
	 * function use to add task
     * make ajax put json data to server
     */

    function saveTaskToServlet(data, action) {
        console.log(data);
		$.ajax("TaskServlet",{
			"type":"post",
			dataType:"json",
            "data": {"action": action,
                "task" : JSON.stringify(data)
            }
		}).done();
    }
    /**
	 * makes json call to server to get task list.
	 * currently just testing this and writing return value out to console
	 * 111917kl
     */
	function retrieveTasksServer() {
		var retrieveUserId = $(taskPage).find("#retrieveUserId").val();
		// if (!retrieveUserId){
		// 	retrieveUserId = 1;
		// }
        $.ajax("TaskServlet", {
            "type": "get",
			dataType: "json",
            "data": {
                 "userId": retrieveUserId ? retrieveUserId : ''
            }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    /**
	 * 111917kl
	 * callback for retrieveTasksServer
     * @param data
     */
    function displayTasksServer(data) { //this needs to be bound to the tasksController -- used bind in retrieveTasksServer 111917kl
    	console.log(data);
        tasksController.loadServerTasks(data);
    }

    function deleteTaskServer(task) {
    	console.log(task)
    	$.ajax("TaskServlet", {
			"type": "post",
			datatype: "json",
			"data": {"action": "delete",
					"task" : JSON.stringify(task)
			}
		}).done(deleteTaskServerDone);
	}

	function deleteTaskServerDone(data) {
		console.log(data);
	}

    function completeTaskServer(task) {
        $.ajax("TaskServlet", {
            "type": "post",
            datatype: "json",
            "data": {"action": "complete",
                "task" : JSON.stringify(task)
            }
        }).done(completeTaskServerDone);
	}

	function completeTaskServerDone(data) {
		console.log(data);
    }
	
	function taskCountChanged() {
		var count = $(taskPage).find( '#tblTasks tbody tr').length;
		$('footer').find('#taskCount').text(count);
	}
	
	function clearTask() {
		$(taskPage).find('form').fromObject({});
	}
	
	function renderTable() {
		$.each($(taskPage).find('#tblTasks tbody tr'), function(idx, row) {
			var due = Date.parse($(row).find('[datetime]').text());
			if (due.compareTo(Date.today()) < 0) {
				$(row).addClass("overdue");
			} else if (due.compareTo((2).days().fromNow()) <= 0) {
				$(row).addClass("warning");
			}
		});
	}
	
	return { 
		init : function(page, callback) { 
			if (initialised) {
				callback()
			} else {
				taskPage = page;
				storageEngine.init(function() {
					storageEngine.initObjectStore('task', function() {
						callback();
					}, errorLogger) 
				}, errorLogger);	 				
				$(taskPage).find('[required="required"]').prev('label').append( '<span>*</span>').children( 'span').addClass('required');
				$(taskPage).find('tbody tr:even').addClass('even');
				
				$(taskPage).find('#btnAddTask').click(function(evt) {
					evt.preventDefault();
					$(taskPage).find('#taskCreation').removeClass('not');
				});

                /**	 * 11/19/17kl        */
                $(taskPage).find('#btnRetrieveTasks').click(function(evt) {
                    evt.preventDefault();
                    console.log('making ajax call');
                    retrieveTasksServer();
                });
				
				$(taskPage).find('#tblTasks tbody').on('click', 'tr', function(evt) {
					$(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
				});	
				
				$(taskPage).find('#tblTasks tbody').on('click', '.deleteRow', 
					function(evt) {
						let taskId = $(evt.target).data().taskId;
                        storageEngine.findById('task', taskId, function(task) {
                            deleteTaskServer(task);
                        }, errorLogger);

						storageEngine.delete('task', taskId,
							function() {
								$(evt.target).parents('tr').remove(); 
								taskCountChanged();
							}, errorLogger);

					}
				);
				
				$(taskPage).find('#tblTasks tbody').on('click', '.editRow', 
					function(evt) { 
						$(taskPage).find('#taskCreation').removeClass('not');
						storageEngine.findById('task', $(evt.target).data().taskId, function(task) {
							$(taskPage).find('form').fromObject(task);
						}, errorLogger);
					}
				);
				
				$(taskPage).find('#clearTask').click(function(evt) {
					evt.preventDefault();
					clearTask();
				});
				
				$(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function(evt) {
					let taskId = $(evt.target).data().taskId;
					storageEngine.findById('task', taskId, function(task) {
						task.complete = true;
						task.status = "Completed";
                        completeTaskServer(task);
						storageEngine.save('task', task, function() {
							tasksController.loadTasks();
						},errorLogger);
					}, errorLogger);
				});
				
				$(taskPage).find('#saveTask').click(function(evt) {
					evt.preventDefault();
					if ($(taskPage).find('form').valid()) {
						var task = $(taskPage).find('form').toObject();
                        if (task.id === "") {
                            task.id = 0;
                            saveTaskToServlet(task, "insert");
                        } else {
                            saveTaskToServlet(task, "update");
						}

						storageEngine.save('task', task, function() {
							$(taskPage).find('#tblTasks tbody').empty();
							tasksController.loadTasks();
							clearTask();
							$(taskPage).find('#taskCreation').addClass('not');
						}, errorLogger);
					}
				});

				// Sort task by priority or due date by clicking on table row header
                $(taskPage).find('#tblTasks thead > tr > th').each(function () {
					var ascendant = false;

                    $(this).click(function (evt) {
                        let sortFunction = sortCriteria[evt.target.id];

                        if (sortFunction != null) {
                            storageEngine.findAll('task', function(tasks) {
                                tasks.sort(sortFunction(ascendant));

                                $(taskPage).find('#tblTasks tbody').empty();
                                $('#taskRow').tmpl(tasks).appendTo($(taskPage).find('#tblTasks tbody'));
                                renderTable();
                            }, errorLogger);

                            ascendant = !ascendant;
                        }
                    });
                });

				initialised = true;
			}
		},
        /**
		 * 111917kl
		 * modification of the loadTasks method to load tasks retrieved from the server
         */
		loadServerTasks: function(tasks) {
			storageEngine.initializedObjectStores = {};	// reset cache

            $(taskPage).find('#tblTasks tbody').empty();
            $.each(tasks, function (index, task) {
            	if (task.status === "Completed") {
                    task.complete = true;
				} else {
            		task.complete = false;
				}
                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                taskCountChanged();
                console.log('about to render table with server tasks');
                renderTable(); //--skip for now, this just sets style class for overdue tasks 111917kl

				// Save server task to local storage
                storageEngine.save('task', task, function() {
                }, errorLogger);
            });
		},
		loadTasks : function() {
			$(taskPage).find('#tblTasks tbody').empty();
			storageEngine.findAll('task', function(tasks) {
				tasks.sort(function(o1, o2) {
					return Date.parse(o1.dueDate).compareTo(Date.parse(o2.dueDate));
				});
				$.each(tasks, function(index, task) {
                    if (task.status === "Completed") {
                        task.complete = true;
                    } else {
                        task.complete = false;
                    }
					$('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
					taskCountChanged();
					renderTable();
				});
			}, errorLogger);
		} 
	} 
}();
