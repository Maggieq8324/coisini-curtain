import {Paging} from "../utils/paging";

class Search{
    static search(q){
        return new Paging({
            url:`/search?q=${q}`
        }, 3)
    }
}

export {
    Search
}