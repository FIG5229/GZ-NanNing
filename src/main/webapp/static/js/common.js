/*勾选所有的复选框*/
chooseAll = function (obj,checkBoxsName) {
    if (obj.checked) {
        var checkboxs = document.getElementsByName(checkBoxsName);
        for (var i = 0; i < checkboxs.length; i++) {
            checkboxs[i].checked = true;
        }
    } else {
        var checkboxs = document.getElementsByName(checkBoxsName);
        for (var i = 0; i < checkboxs.length; i++) {
            checkboxs[i].checked = false;
        }
    }
};
//得到被选中的checkbox的数据id
function getIds(checkBoxsName) {
    var ids = [];
    $("input:checkbox[type=checkbox]:checked").each(function () {
        if ($(this).val() != "on"){
            ids.push($(this).val());
        }
    });
    return ids;
};
function deleteByIds(url,checkAllId,checkBoxsName) {
    var delIds = getIds(checkBoxsName);
    if (delIds.length > 0) {
        $.ajax({
            type:"post",
            url:url,
            data:{ids:delIds},
            dataType:"json",
            success:function(data){
                $.jBox.tip(data.message);
                resetCheckBox(checkAllId,checkBoxsName);
                location.reload();
            }
        })
    } else {
        $.jBox.tip('请先选择要删除的内容');
    }
};

/**
 * resetCheckBox方法报错，导致在绩效的一些页面无法使用，此方法注释掉
 * @param url
 * @param checkAllId
 * @param checkBoxsName
 */
function deleteByIdsBeta(url,checkAllId,checkBoxsName) {
    var delIds = getIds(checkBoxsName);
    if (delIds.length > 0) {
        $.ajax({
            type:"post",
            url:url,
            data:{ids:delIds},
            dataType:"json",
            success:function(data){
                $.jBox.tip(data.message);
                // resetCheckBox(checkAllId,checkBoxsName);
                location.reload();
            }
        })
    } else {
        $.jBox.tip('请先选择要删除的内容');
    }
};
function resetCheckBox(checkAllId,checkBoxsName) {
    var checkAllBox = document.getElementById(checkAllId);
    checkAllBox.checked = false;
    var checkboxs = document.getElementsByName(checkBoxsName);
    for (var i = 0; i < checkboxs.length; i++) {
        checkboxs[i].checked = false;
    }
};