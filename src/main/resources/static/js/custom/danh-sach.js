$(document).ready(function () {
  $(".accordions-item.active .accordions-body").slideDown();
  $(".accordions-header").click(function () {
    $(this).parent().toggleClass("active");
    $(this).parent().children(".accordions-body").slideToggle();
  });
});
