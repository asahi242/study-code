function Mapping(tagid){
    this.map = new Map();
    this.getNodeById(tagid);
}
function Mapping(tagid,attr){
    this.map = new Map();
    this.getNodeById(tagid,attr);
}
Mapping.prototype={
    //获取ID标签下的所有带name的标签
    getNodeById:function(tagid,attr){
        var children = $("#"+tagid).children();
        for(var i=0;i<children.length;i++){
            var name = children.eq(i).attr(attr);
            this.map.set(name,children.eq(i));
        }
    },
    //对应标签赋值
    setValue:function(name,value){
        this.map.get(name).val(value);
    },
    //获取ID标签下的所有标签
    getNodeById:function(tagid){
        var children = $("#"+tagid).children();
        for(var i=0;i<children.length;i++){
            this.map.set(i,children.eq(i));
        }
    },
    //对应标签赋值
    setValue:function(index,value){
        this.map.get(index).val(value);
    },
    //循环赋值
    setLoopValue:function(tagid,size){

    }

}
Mapping.prototype.constructor = Mapping;
