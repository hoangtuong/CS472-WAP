usersController = function() {

    function errorLogger(errorCode, errorMessage) {
        console.log(errorCode +':'+ errorMessage);
    }

    var taskPage;
    var initialised = false;

    function displayUsers(users) {
        console.log(users);
        usersController.loadServerUsers(users);
    }

    return {
        init : function(page, callback) {
            if (initialised) {
                callback()
            } else {
                taskPage = page;
                storageEngine.init(function() {
                    storageEngine.initObjectStore('user', function() {
                        callback();
                    }, errorLogger)
                }, errorLogger);

                initialised = true;
            }
        },

        retrieveUserServer: function() {
            $.ajax("UserServlet", {
                "type": "get",
                dataType: "json",

            }).done(displayUsers.bind())
        }, //need reference to the tasksController object

        /**
         * 111917kl
         * modification of the loadTasks method to load tasks retrieved from the server
         */
        loadServerUsers: function(users) {
            // storageEngine.initializedObjectStores = {};	// reset cache
            let userIdSelect = $(taskPage).find('#taskCreation #userId');
            let retrieveUserId = $(taskPage).find('#retrieveUserId');

            console.log(retrieveUserId);

            userIdSelect.empty();

            // Add an empty <option>
            retrieveUserId.append($('<option>', {
                value : '',
                text : ''
            }));

            $.each(users, function (index, user) {
                userIdSelect.append($('<option>', {
                    value : user.id,
                    text : user.name
                }));

                retrieveUserId.append($('<option>', {
                    value : user.id,
                    text : user.name
                }));

                // Save server task to local storage
                storageEngine.save('user', user, function() {
                }, errorLogger);
            });
        },
        // loadUsers : function() {
        //     storageEngine.findAll('user', function(users) {
        //         // users.sort(function(o1, o2) {
        //         //     return Date.parse(o1.dueDate).compareTo(Date.parse(o2.dueDate));
        //         // });
        //         $.each(users, function(index, user) {
        //             // $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
        //             // taskCountChanged();
        //             // renderTable();
        //         });
        //     }, errorLogger);
        // }
    }
}();
