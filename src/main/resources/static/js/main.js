
function jsInUser(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#tbody-user').html(response);
                },
            });
        });
    })

    $(document).ready(function() {
        $('.btn-show-details').on('click', function() {
            var userId = $(this).data('id_member');
            var firstname = $(this).data('first_name')
            var lastname = $(this).data('last_name')
            var email = $(this).data('email')
            var phone = $(this).data('phone')
            var gender = $(this).data('gender')
            var address = $(this).data('address')
            var dob = $(this).data('birthday')

            // alert(userId)
            $('#userIdEdit').attr('value', userId);
            $('#firstname_edit').attr('value', firstname);
            $('#lastname_edit').attr('value', lastname);
            $('#email_edit').attr('value', email);
            $('#address_edit').attr('value', address);
            $('#phone_edit').val(phone);
            $('#date_edit').val(dob);
            $('#gender_edit').val(gender);

        });
    });

    $(document).ready(function() {
        $('.btn-show-details-delete').on('click', function() {
            var id = $(this).data('id_member');

            $('#userIdDelete').attr('value', id);

            $('#userIdDelete-para').html('Are you sure to delete '+ id + ' ?');

        });
    });
}

function jsInAccount(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#tbody-account').html(response);
                },
            });
        });
    })
}

function jsInProduct(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#tbody-product').html(response);

                        $('.btn-show-details').on('click', function() {
                            var id = $(this).data('id');
                            var name = $(this).data('name')
                            var import_price = $(this).data('import')
                            var retail = $(this).data('retail')
                            var category = $(this).data('category')
                            var quan = $(this).data('quan')
                            var date = $(this).data('date')
                            var des = $(this).data('des')

                            $('#productIdEdit').attr('value', id);
                            $('#name-edit').attr('value', name);
                            $('#Iprice-edit').attr('value', import_price);
                            $('#Rprice-edit').attr('value', retail);
                            $("#type-edit").val(category);
                            $('#quan-edit').val(quan);
                            $('#des-edit').val(des);
                        });

                        $('.btn-show-details-delete').on('click', function() {
                            var id = $(this).data('id');
                            // alert(id)

                            $('#productIdDelete').attr('value', id);
                            $('#delete-modal-para').html("Are you sure to delete "+ id+" ?");

                        });
                },
            });
        });
    })

    $(document).ready(function() {
        $('.btn-show-details').on('click', function() {
            var id = $(this).data('id');
            var name = $(this).data('name')
            var import_price = $(this).data('import')
            var retail = $(this).data('retail')
            var category = $(this).data('category')
            var quan = $(this).data('quan')
            var date = $(this).data('date')
            var des = $(this).data('des')

            $('#productIdEdit').attr('value', id);
            $('#name-edit').attr('value', name);
            $('#Iprice-edit').attr('value', import_price);
            $('#Rprice-edit').attr('value', retail);
            // $("#type-edit").val(category);
            $('#quan-edit').val(quan);
            $('#des-edit').val(des);
        });
    });

    $(document).ready(function() {
        $('.btn-show-details-delete').on('click', function() {
            var id = $(this).data('id');
            // alert(id)

            $('#productIdDelete').attr('value', id);
            $('#delete-modal-para').html("Are you sure to delete "+ id+" ?");

        });
    });
}

function jsInCustomer(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#tbody-customer').html(response);
                },
            });
        });
    })

    // showModalInCustomer();
    $(document).ready(function() {
        var cusId = null;
        $('.btn-show-details').on('click', function() {
            cusId = $(this).data('id');
            // console.log(cusId)

            // $("#purchaseCusId").attr('value', cusId)
            $.ajax({
                type: 'GET',
                url: '/customer/purchase-history/' + cusId,
                success: function(response) {

                    $('#tbody').html(response);
                    $('.btn-show-details-history').on('click', function() {
                        cusId = $(this).data('id');
                        var date = $(this).data('date');
                        console.log(cusId)
                        console.log("hiii")

                        $("#order-id").attr('value', cusId)
                        $.ajax({
                            type: 'GET',
                            url: '/customer/order_details/' + cusId+"/"+date,
                            success: function(response) {

                                $('#tbody-details').html(response);
                                // $('#customerModal').modal('show');
                            },
                            error: function(error) {
                                console.error('Error:', error);
                            }
                        });
                    });
                    // $('#customerModal').modal('show');
                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        });
    });
}

function jsInReport(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#tbody').html(response);
                },
            });
        });
    })

    $(document).ready(function() {
        var orderId = null;
        $('.btn-show-details').on('click', function() {
            orderId = $(this).data('id');

            $.ajax({
                type: 'GET',
                url: '/payment/report/order-list/' + orderId,
                success: function(response) {
                    $('#tbody-details').html(response);

                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        });
    });

    $(document).ready(function() {
        // var orderId = null;
        $('#btn-date').on('click', function() {
            // orderId = $(this).data('id');
            var date_start = $("#date-start").val();
            var date_end = $("#date-end").val();
            // console.log(date_start)
            // console.log(date_end)

            $.ajax({
                type: 'GET',
                url: '/payment/report/sort-order-list/' + date_start + '/' + date_end,
                success: function(response) {
                    $('#tbody').html(response);

                    $.ajax({
                        type: 'GET',
                        url: '/payment/report/total/' + date_start + '/' + date_end,
                        success: function(response) {
                            $('#total-items').html(response);

                        },
                        error: function(error) {
                            console.error('Error:', error);
                        }
                    });

                    var orderId = null;
                    $('.btn-show-details').on('click', function() {
                        orderId = $(this).data('id');

                        $.ajax({
                            type: 'GET',
                            url: '/payment/report/order-list/' + orderId,
                            success: function(response) {
                                $('#tbody-details').html(response);

                            },
                            error: function(error) {
                                console.error('Error:', error);
                            }
                        });
                    });

                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        });
    });
}

function jsInHome(){
    $(document).ready(function() {
        $('.page-link').on('click', function() {
            var page = $(this).data('page_number');
            console.log(page)
            $.ajax({
                type: 'GET',
                url: ''+page+'/ajax',
                success: function(response) {
                    $('#main-home-product__item').html(response);
                },
            });
        });
    })

    // search
    // $(document).ready(function() {
    //     $('#search-navbar').on('input', function() {
    //         var character = $(this).val();
    //         console.log(character)
    //         $.ajax({
    //             type: 'GET',
    //             url: '/search-home/'+character,
    //             success: function(response) {
    //                 $('#main-home-product__item').html(response);
    //             },
    //         });
    //     });
    // })

    // cal money
    $(document).ready(function() {
        $('#customer_given').on('input', function() {
            var number = $(this).val();
            console.log(number)
            $.ajax({
                type: 'GET',
                url: 'calculate/'+number,
                success: function(response) {
                    $('#given_change').html(response+"$");
                    $('#total_amount').attr("value" , response);
                },
            });
        });
    })

    // category
    $(document).ready(function(){
        var categoryType = null;
        $('.btn-shows-category').on('click', function() {
            categoryType = $(this).data('id');
            $.ajax({
                type: 'GET',
                url: '/home/category/' + categoryType,
                success: function(response) {

                    $('#main-home-product__item').html(response);

                },
            });
        });

        // add product
        $('.main-home-product__item').on('click', function() {
            var id = $(this).data('id');
            var phone = $('#phone').val();
            var quan = $(this).data('quan')
            if (phone === '') {
                alert('You need to find customer first!');
            }

            $('#phone_number_quan').attr('value', phone);
            $('#product_id').attr('value', id);
            $('#quan_stock').attr('value', quan);

        });

        // remove product
        $('.remove-items').on('click', function() {
            var odt_id = $(this).data('id');
            var ord_id = $(this).data('ord_id');
            var pro_id = $(this).data('pro_id');
            var quan = $(this).data('quan');
            $('#productID').attr('value', odt_id);
            $('#ordID').attr('value', ord_id);
            $('#product-id_real').attr('value', pro_id);
            $('#quantity-remove').attr('value', quan)
        });

        // cancel
        $('.cancel-btn').on('click', function() {
            var phone = $("#phone").val();

            $('#phone-cancel').attr('value', phone);

        });
    });

    // find phone
    $(document).ready(function() {
        $('#btn-find-phone').on('click', function() {
            var phone = $("#phone").val();
            console.log(phone)
            $.ajax({
                type: 'GET',
                url: '/find/'+phone,
                success: function(response) {

                    $('#customerFindByPhone').html(response);
                },
            });
        });
    })
}

function searchHome(){
    let input = document.getElementById('search-navbar');
    let list = document.getElementById('main-home-product__item');

    input.addEventListener('input', function() {
        let query = input.value.toLowerCase();
        let items = list.getElementsByClassName('main-home-product__item');

        for (let i = 0; i < items.length; i++) {
            let item = items[i];
            let text = item.textContent.toLowerCase();

            if (text.includes(query)) {
                if(item.classList.contains('d-none')){
                    item.classList.remove('d-none');
                }
            } else {
                if(!item.classList.contains('d-none')){
                    item.classList.add('d-none');
                }
            }
        }
    });
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


