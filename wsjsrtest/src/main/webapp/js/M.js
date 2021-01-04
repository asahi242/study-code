function M(){
    this.map = {}
}
M.prototype = {
    put:function(key,value){
        this.map[key] = value;
    },
    get:function(key){
        if(this.map.hasOwnProperty(key)){
            return this.map[key];
        }else{
            return null;
        }
    }

}
M.prototype.constructor = M;