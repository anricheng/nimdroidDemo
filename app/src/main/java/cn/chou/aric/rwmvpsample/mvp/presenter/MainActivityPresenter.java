package cn.chou.aric.rwmvpsample.mvp.presenter;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import cn.chou.aric.baselibrary.mvp.BasePresenter;
import cn.chou.aric.baselibrary.model.AppService;
import cn.chou.aric.baselibrary.rx.RxUtils;
import cn.chou.aric.rwmvpsample.mvp.view.MainActivityView;


public class MainActivityPresenter extends BasePresenter<MainActivityView> {

    AppService mAppService;
    RxAppCompatActivity mRxActivity;
    String mockResult;

    @Inject
    public MainActivityPresenter(AppService appService, RxAppCompatActivity rxActivity) {
        mAppService = appService;
        mRxActivity = rxActivity;
        mockResult =  "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"成功!\",\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"title\": \"日诗\",\n" +
                "            \"content\": \"欲出未出光辣达，千山万山如火发。|须臾走向天上来，逐却残星赶却月。\",\n" +
                "            \"authors\": \"宋太祖\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句\",\n" +
                "            \"content\": \"未离海底千山黑，才到天中万国明。\",\n" +
                "            \"authors\": \"宋太祖\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"登戎州江楼闲望\",\n" +
                "            \"content\": \"满目江山四望幽，白云高卷嶂烟收。|日回禽影穿疏木，风递猿声入小楼。|远岫似屏横碧落，断帆如叶截中流。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"雪\",\n" +
                "            \"content\": \"片片飞来静又闲，楼头江上复山前。|飘零尽日不归去，帖破清光万里天。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"云\",\n" +
                "            \"content\": \"因登巨石知来处，勃勃元生绿藓痕。|静即等闲藏草木，动时顷刻遍乾坤。|横天未必朋元恶，捧日还曾瑞至尊。|不独朝朝在巫峡，楚王何事谩劳魂。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其一\",\n" +
                "            \"content\": \"若教作镇居中国，争得泥金在泰山。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其二\",\n" +
                "            \"content\": \"才闻暖律先偷眼，既待和风始展眉。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其三\",\n" +
                "            \"content\": \"嚼处春冰敲齿冷，咽时雪液沃心寒。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其四\",\n" +
                "            \"content\": \"蒙君知重惠琼实，薄起金刀钉玉深。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其五\",\n" +
                "            \"content\": \"深妆玉瓦平无垅，乱拂芦花细有声。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其六\",\n" +
                "            \"content\": \"片逐银蟾落醉觥。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其七\",\n" +
                "            \"content\": \"巧剪银花乱，轻飞玉叶狂。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"句  其八\",\n" +
                "            \"content\": \"寒艳芳姿色尽明。\",\n" +
                "            \"authors\": \"幸夤逊\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 秦淮\",\n" +
                "            \"content\": \"一气东南王斗牛，祖龙潜为子孙忧。|金陵地脉何曾断，不觉真人已姓刘。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 石头城\",\n" +
                "            \"content\": \"五城楼雉各相望，山水英灵宅帝王。|此地定由天造险，古来长恃作金汤。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 段石冈\",\n" +
                "            \"content\": \"孙吴纪德旧刊碑，草没蟠螭与伏龟。|惆怅冈头三段石，至今犹似鼎分时。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 北渠\",\n" +
                "            \"content\": \"金殿分来玉砌流，黑龙湖彻凤池头。|后庭花落恩波断，翻与南唐作御沟。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 新亭\",\n" +
                "            \"content\": \"满目江山异洛阳，昔人何必重悲伤。|倘能戮力扶王室，当自新亭复故乡。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 天阙山\",\n" +
                "            \"content\": \"牛头天际碧凝岚，王导无稽亦妄谈。|若指远山为上阙，长安应合指终南。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"金陵览古 东山\",\n" +
                "            \"content\": \"镇物高情济世才，欲随猿鹤老岩隈。|山花处处红妆面，髣髴如初拥妓来。\",\n" +
                "            \"authors\": \"朱存\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    public void getPoetry() {
        mView.get().showLoading();
        mAppService.getPoetry(1, 20)
                .compose(RxUtils.switchIOAndMainThreadTransformer())
                .compose(mRxActivity.bindToLifecycle())
                .subscribe(new RxUtils.SimpleSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (mView.get() != null) {
                            mView.get().hideLoading();
                            mView.get().showResult(o.toString());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mView.get() != null) {
                            mView.get().hideLoading();
                            mView.get().showResult(mockResult);
                        }
                    }
                });
    }
}
