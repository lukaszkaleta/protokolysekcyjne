$(document).ready(function () {

  var $tabs = $('#table-draggable');

  $("tbody.connectedSortable")
    .sortable({
      connectWith: ".connectedSortable",
      appendTo: $tabs,
      helper: "clone",
      zIndex: 999990,
        update: function( event, ui ) {
          var trs = this.children;
          var ordered = [];
          for(var i = 0; i < trs.length; i++) {
            ordered.push(trs[i].id);
          }
          $.ajax({
            url: '/protokolysekcyjne/protocol/clinicalDiagnosis/reorder/234',
            type: 'PUT',
            data: ordered,
            success: function(data) {
              alert('Load was performed.');
            }
          });
        }
    })
    .disableSelection();

  var $tab_items = $("tbody.connectedSortable", $tabs).droppable({
    accept: ".connectedSortable tr",
    hoverClass: "ui-state-hover",

    drop: function (event, ui) {
      // Upuszczamy
      var id = ui.draggable[0].id;
      return true;
    },
    create: function( event, ui ) {
      // tu mozna obliczyc pozycje
      return true;
    },
    activate: function( event, ui ) {
      // Zaczynamy ciagnoc
      return true;
    },
    deactivate: function( event, ui ) {
      // po upuszczeniu
      return true;
    },
    out: function( event, ui ) {
      // Wychodzimy poza dragable
      return true;
    },
    over: function( event, ui ) {
      console.log("Over:" + ui.draggable[0].id);
      return true;
    }
  });

});