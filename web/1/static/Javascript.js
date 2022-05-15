//time
function getDate()
{
    var date = new Date();                 
    var date1 = date.toLocaleString();
    var div1 = document.getElementById("times");
    div1.innerHTML = date1;
}

setInterval("getDate()",1000);
  var $image = $('#image')
  // 1.2 配置选项
  const options = {
    // 纵横比
    aspectRatio: 1,
    // 指定预览区域
    preview: '.img-preview'
  }

  // 1.3 创建裁剪区域
  $image.cropper(options)
