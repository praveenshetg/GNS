function createDynamiTable(group){
// EXTRACT VALUE FOR HTML HEADER. 
// ('Book ID', 'Book Name', 'Category' and 'Price')
var col = [];
for (var i = 0; i < group.length; i++) {
    for (var key in group[i]) {
        if (col.indexOf(key) === -1) {
            col.push(key);
        }
    }
}

// CREATE DYNAMIC TABLE.
var table = document.createElement("table")
table.setAttribute("class", "grp-table" );
table.setAttribute("id","data");

// CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

var tr = table.insertRow(-1);                   // TABLE ROW.

for (var i = 0; i < col.length; i++) {
    var th = document.createElement("th");      // TABLE HEADER.
    th.innerHTML = col[i].toUpperCase();
    tr.appendChild(th);
}
tr.appendChild(th);
// ADD JSON DATA TO THE TABLE AS ROWS.
for (var i = 0; i < group.length; i++) {

    tr = table.insertRow(-1);

    for (var j = 0; j < col.length; j++) {
        var tabCell = tr.insertCell(-1);
        tabCell.innerHTML = group[i][col[j]];
        if( j==col.length-1){
          tabCell.innerHTML = '<div onclick=editGroup("'+group[i][col[j]]+'")>Edit</div> <a href="'+group[i][col[j]]+'">Delete</a>';
        }
    }
}

// FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
var divContainer = document.getElementById("showData");
divContainer.innerHTML = "";
divContainer.appendChild(table);
}

function paginateTable(id,rows){
	console.log("paginating table for table #"+id+" and rows "+rows)
	$('#'+id).after('<div id="nav"></div>');
    var rowsShown = rows;
    var rowsTotal = $('#'+id+' tbody tr').length;
    var numPages = rowsTotal/rowsShown;
    for(i = 0;i < numPages;i++) {
        var pageNum = i + 1;
        $('#nav').append('<a href="#" rel="'+i+'">'+pageNum+'</a> ');
    }
    $('#'+id+' tbody tr').hide();
    $('#'+id+' tbody tr').slice(0, rowsShown).show();
    $('#nav a:first').addClass('active');
    $('#nav a').bind('click', function(){

        $('#nav a').removeClass('active');
        $(this).addClass('active');
        var currPage = $(this).attr('rel');
        var startItem = currPage * rowsShown;
        var endItem = startItem + rowsShown;
        $('#'+id+' tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
        css('display','table-row').animate({opacity:1}, 300);
    });
}