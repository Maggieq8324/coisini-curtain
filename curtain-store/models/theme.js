import {Http} from "../utils/http";

export class Theme {
    static locationA = "t-1";
    static locationE = "t-2";
    static locationF = "t-3";
    static locationH = "t-4";

    themes = [];

    async getThemes() {
        this.themes = await Http.request({
            url: '/theme/getThemes'
        });
    }

    getHomeLocationA() {
        return this.themes.find(item => {return item.name === Theme.locationA});
    }

    getHomeLocationE() {
        return this.themes.find(item => {return item.name === Theme.locationE});
    }

    getHomeLocationF() {
        return this.themes.find(item => {return item.name === Theme.locationF});
    }

    getHomeLocationH() {
        return this.themes.find(item => {return item.name === Theme.locationH});
    }

    static getHomeLocationESpu() {
        return Theme.getThemeSpuByName(Theme.locationE);
    }

    static getThemeSpuByName(name) {
        return Http.request({
            url: `/theme/name/${name}/with_spu`
        })
    }
}