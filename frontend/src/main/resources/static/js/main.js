$(function () {
    $("#datatable").DataTable({
        "paging": true,
        "pagingType": "first_last_numbers",
        "lengthMenu": [[15, 25, 50, -1], [15, 25, 50, "Все"]],
        "searching": false,
        "info": true,
        "language": {
            "emptyTable": "Таких книг в базе нет",
            "infoEmpty": "",
            "paginate": {
                "first": "<<",
                "last": ">>",
                "next": "Следующая",
                "previous": "Предыдущая"
            },
            "info": "",
            "lengthMenu": "Отобразить _MENU_ книг",
        },
        "order": [
            [
                6,
                "desc"
            ]
        ]
    });
});