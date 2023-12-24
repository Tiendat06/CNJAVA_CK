
function showModelInUser(){
    // alert(btn_id);
    $(document).ready(function(){
        // document.addEventListener('DOMContentLoaded', function () {
        //     var openModalButton = document.getElementById('#'+btn_id);
        //     var userIdInput = document.getElementById('userIdEdit');
        //
        //     alert(userIdInput)
        //     alert(openModalButton)
        //
        //     openModalButton.addEventListener('click', function () {
        //         var userId = this.getAttribute('data-id');
        //         userIdInput.value = userId;
        //     });
        // });
        $('#edit-user').on('show.bs.modal', function (e){
            var button = $(e.relatedTarget)

            $('#userIdEdit').attr('value', button);
        })
    })
}

function showModalInUserDelete(btn_id){
    document.addEventListener('DOMContentLoaded', function () {

        var openModalButton = document.querySelector('#'+btn_id);
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

// function showModalInHome(id){
//     document.addEventListener('DOMContentLoaded', function () {
//         // add-product
//         console.log(id);
//         var openModalButton = document.querySelector('#'+id);
//         var userIdInput = document.getElementById('product_id');
//
//         openModalButton.addEventListener('click', function () {
//             var userId = this.getAttribute('data-id');
//             userIdInput.value = userId;
//         });
//     });
//
//     // remove product
//     document.addEventListener('DOMContentLoaded', function () {
//         var openModalButton = document.querySelector('#'+id);
//         var userIdInput = document.getElementById('productID');
//
//         openModalButton.addEventListener('click', function () {
//             var userId = this.getAttribute('data-id');
//             userIdInput.value = userId;
//         });
//     });
// }

function jsInHome(){



    // $(document).ready(function() {
    //     $("#btn-pay").on("click", function() {
    //         // var number = $(this).val();
    //         var number = 3;
    //         console.log(number)
    //
    //         $.ajax({
    //             type: 'GET',
    //             url: "/calculate_money/3",
    //             success: function(response) {
    //
    //                 $('#given_change').html(response);
    //
    //             },
    //             // error: function(error) {
    //             //     console.error('Error:', error);
    //             // }
    //         });
    //
    //     });
    // });
}

function handleUploadImg() {
    const fileInput = document.getElementById('fileInput');
    const img = document.getElementById('profile-img');

    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            img.src = e.target.result;
        };

        reader.readAsDataURL(file);
    }
}


