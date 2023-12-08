var  urlLichSu = "http://localhost:8081/api/v1/user/lich-su-giao-dich";
$(document).ready(()=>{
    callApiLichSuGiaoDichStaff(urlLichSu);
});


function callApiLichSuGiaoDichStaff(url){
    $.ajax({
        type:"GET",
        url:url,
        success: function(response){
            console.log(response);
            appLichSuGiaoDich.hoaDon =  response.content.data;
            appLichSuGiaoDich.totalPage =  response.content.totalPages;
            appLichSuGiaoDich.pageNumber =  response.content.currentPage;
            appLichSuGiaoDich.pageSize =  response.content.pageSize;
        },
        error: function(error){
            console.log(error);
        }
    })
};


var appLichSuGiaoDich = new Vue({
    el:"#appLichSuGiaoDich",
    data:{
        hoaDon:{
            id:"",
            ngayTao:"",
            soDienThoaiNguoiDat:"",
            tienCoc:0,
            tongTien:0,
            email:"",
            tenNguoiDat:"",
            trangThai:0,
            hoaDonSanCaStaffList:[],
        },
        listDichVuSanBong:[],
        valueSeach:"",
        totalPage:0,
        pageNumber:0,
        pageSize:0,
        indexSeachLS:0,
    },
    methods:{
        valueTrangThaiHoaDonSanCa(trangThai){
            switch (trangThai){
                case 0 :
                    return "Chờ Nhận Sân";
                case 1 :
                    return "Đã Check In";
                case 2 :
                    return "Chưa Thanh Toán";
                case 3 :
                    return "Đã Thanh Toán";
                case 4 :
                    return "Đã Hủy";
                case 5 :
                    return "Chờ Đổi Sân";
            }
        },
        findByIdHoaDonSanCa(id){
            for(let i = 0; i<appLichSuGiaoDich.hoaDon.length;i++){
                for (let j= 0; j<appLichSuGiaoDich.hoaDon[i].hoaDonSanCaStaffList.length;j++){
                    if(appLichSuGiaoDich.hoaDon[i].hoaDonSanCaStaffList[j].id === id){
                        appLichSuGiaoDich.listDichVuSanBong = appLichSuGiaoDich.hoaDon[i].hoaDonSanCaStaffList[j];
                        return;
                    }
                }
            }
        },
        currencyNumberVN(number){
            return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
        },
        formatDate(date){
            return moment(date).format("DD-MM-YYYY");
        },
        formatNgayGio(date){
            return moment(date).format("HH:mm:ss DD-MM-YYYY");
        },
        searchByNamAndSdt(event){
            var  url = "http://localhost:8081/api/v1/user/lich-su-giao-dich";
            var  urlNew ="";
            if(event.target.value == '' || event.target.value == null){
                callApiLichSuGiaoDichStaff("http://localhost:8081/api/v1/user/lich-su-giao-dich");
                return;
            }
            urlNew = url+ "/search?ten="+appLichSuGiaoDich.valueSeach+"&sdt="+appLichSuGiaoDich.valueSeach;
            appLichSuGiaoDich.indexSeachLS = 1;
            callApiLichSuGiaoDichStaff(urlNew);
        },
        pageTionLS(value) {
            var  url = "http://localhost:8081/api/v1/user/lich-su-giao-dich";
            var  urlNew ="";
            if (parseInt(appLichSuGiaoDich.indexSeachLS) === 1) {
                urlNew = url+ "/search?ten="+appLichSuGiaoDich.valueSeach+"&sdt="+appLichSuGiaoDich.valueSeach+"&page="+value+"&size="+appLichSuGiaoDich.pageSize;
            } else {
                urlNew = url+"?page=" + value + "&size=" + appLichSuGiaoDich.pageSize;

            }
           callApiLichSuGiaoDichStaff(urlNew);
        },
        nextPageLS() {
            if (parseInt(appLichSuGiaoDich.pageNumber) + 1 == parseInt(appLichSuGiaoDich.totalPage)) {
                return;
            }
            this.pageTionLS(parseInt(appLichSuGiaoDich.pageNumber) + 1);
        },
        previuosLS() {
            if (parseInt(appLichSuGiaoDich.pageNumber) == 0) {
                return;
            }
            this.pageTionLS(parseInt(appLichSuGiaoDich.pageNumber) - 1);
        },
        pageSizeSelectLS(event) {
            var  url = "http://localhost:8081/api/v1/user/lich-su-giao-dich";
            var  urlNew ="";
            if (parseInt(appLichSuGiaoDich.indexSeachLS) === 1) {
                urlNew = url+ "/search?ten="+appLichSuGiaoDich.valueSeach+"&sdt="+appLichSuGiaoDich.valueSeach+"&size="+event.target.value;

            } else {
                urlNew = url+"?size=" + event.target.value;

            }
           callApiLichSuGiaoDichStaff(urlNew);
        },
    }
})