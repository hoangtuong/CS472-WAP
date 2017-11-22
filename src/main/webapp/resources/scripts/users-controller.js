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

    function insertUserPopUp() {
        let username = prompt("Please enter your name:");
        if (username === null || username === "") {
            console.log("User cancelled the prompt.");
        } else {
            insertUserServer(username, "abc@gmail.com");
        }
    }

    function insertUserServer(username, email) {
        $.ajax("UserServlet", {
            "type": "post",
            dataType: "json",
            "data": {"username": username, "email": email}
        }).done(function(user) {
            console.log(user);
            let userIdSelect = $(taskPage).find('#taskCreation #userId');
            userIdSelect.append($('<option>', {
                value : user.id,
                text : user.name,
                selected: "selected"
            }));
            storageEngine.save('user', user, function() {
            }, errorLogger);
        }).fail(function(xhr, status, exception) {
            console.log(xhr, status, exception);
        });
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
                $(taskPage).find('#addUser').click(function(evt) {
                    evt.preventDefault();
                    insertUserPopUp();
                });
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
            userIdSelect.empty();

            $.each(users, function (index, user) {
                userIdSelect.append($('<option>', {
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
