
function showModelInUser(){
    document.addEventListener('DOMContentLoaded', function () {
        var openModalButton = document.querySelector('#edit-btn');
        var userIdInput = document.getElementById('userId');

        openModalButton.addEventListener('click', function () {
            var userId = this.getAttribute('data-id');
            userIdInput.value = userId;
        });
    });

    document.addEventListener('DOMContentLoaded', function () {
        var openModalButton = document.querySelector('#delete-btn');
        var userIdInput = document.getElementById('userId-delete');

        openModalButton.addEventListener('click', function () {
            var userId = this.getAttribute('data-id');
            userIdInput.value = userId;
        });
    });
}

function showModalInCustomer(){
    document.addEventListener('DOMContentLoaded', function () {
        var openModalButton = document.querySelector('#customer');
        var userIdInput = document.getElementById('order-id');

        openModalButton.addEventListener('click', function () {
            var userId = this.getAttribute('data-id');
            userIdInput.value = userId;
        });
    });
}

function showModalInHome(id){
    document.addEventListener('DOMContentLoaded', function () {
        // add-product
        console.log(id);
        var openModalButton = document.querySelector('#'+id);
        var userIdInput = document.getElementById('product_id');

        openModalButton.addEventListener('click', function () {
            var userId = this.getAttribute('data-id');
            userIdInput.value = userId;
        });
    });

    // remove product
    document.addEventListener('DOMContentLoaded', function () {
        var openModalButton = document.querySelector('#'+id);
        var userIdInput = document.getElementById('productID');

        openModalButton.addEventListener('click', function () {
            var userId = this.getAttribute('data-id');
            userIdInput.value = userId;
        });
    });
}


