$(document).ready(function () {
  $(".tab-content-item").hide();
  $(".tab-content-item:first-child").fadeIn();

  $(".nav-tabs li").click(function () {
    // add active
    $(".nav-tabs li").removeClass("active");
    $(this).addClass("active");
    // show tab content
    let id_tab_content = $(this).children("a").attr("href");
    $(".tab-content-item").hide();
    $(id_tab_content).fadeIn();

    return false;
  });
});

$(document).ready(function () {
  $(".accordions-item.active .accordions-body").slideDown();
  $(".accordions-header").click(function () {
    $(this).parent().toggleClass("active");
    $(this).parent().children(".accordions-body").slideToggle();
  });
});
