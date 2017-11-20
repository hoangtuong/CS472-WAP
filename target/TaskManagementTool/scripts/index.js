/* jshint esversion: 6 */

function initScreen() {
    $(document).ready(function() {
        tasksController.init($('#taskPage'), function() {
            tasksController.loadTasks();
        });
    });
}
if (window.indexedDB) {
    $.getScript( "scripts/tasks-indexeddb.js" )
        .done(function( script, textStatus ) {
            initScreen();
        })
        .fail(function( jqxhr, settings, exception ) {
            console.log( 'Failed to load indexed db script' );
        });
} else if (window.localStorage) {
    $.getScript( "scripts/tasks-webstorage.js" )
        .done(function( script, textStatus ) {
            initScreen();
        })
        .fail(function( jqxhr, settings, exception ) {
            console.log( 'Failed to load web storage script' );
        });
}