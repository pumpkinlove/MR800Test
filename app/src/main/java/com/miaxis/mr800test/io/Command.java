package com.miaxis.mr800test.io;



/**
 * 密令码
 * @author yc.zhang
 *
 */
public class Command {
	
	//命令码--------------------------------------------------------------
	public static final String CMD_PLAY = "PY";			//播放
	public static final String CMD_APPIONT_PLAY = "AY";	//指定播放
	public static final String CMD_NOTHING = "NT";		//停止无播放资源
	public static final String CMD_SETTINGS = "SE";		//设置
	public static final String CMD_SIGNATURE = "SG";    //触摸测试
	public static final String CMD_TEST = "TE";
	public static final String CMD_RGB = "RGB";
	public static final String CMD_APPRAISE_OVER = "AO";//评价结束
//	public static final String CMD_JS = "JS";			//信息交互
//	public static final String CMD_JF = "JF";			//文件交互
//	public static final String CMD_PJ = "PJ";			//评价
//	public static final String CMD_SG = "SG";			//电子签名
//	public static final String CMD_IN = "IN";			//数字输入
	public static final String CMD_AF = "AF";			//文件新增
//	public static final String CMD_DF = "DF";			//文件删除
//	public static final String CMD_QF = "QF";			//文件查询
//	public static final String CMD_SP = "SP";			//语音播放
	public static final String CMD_LG = "LG";			//柜员签到
//	public static final String CMD_LO = "LO";			//柜员签退
//	public static final String CMD_QS = "QS";			//参数查询
//	public static final String CMD_ST = "ST";			//参数设置
//	public static final String CMD_UV = "UV";			//软件升级
//	public static final String CMD_QA = "QA";			//软件版本查询
//	public static final String CMD_QM = "QM";			//固件版本查询
//	public static final String CMD_QC = "QC";			//客户设备编号查询
	public static final String CMD_WC = "WC";			//客户设备编号写入
	public static final String CMD_QP = "QP";			//厂商设备编号查询
	public static final String CMD_WP = "WP";			//厂商设备编号写入
//	public static final String CMD_RS = "RS";			//设置密码重置
//	public static final String CMD_RB = "RB";			//设备重启
//	public static final String CMD_GS = "GS";			//获取设备状态
//	public static final String CMD_SM = "SM";			//媒体播放
//	public static final String CMD_SI = "SI";           //签名数据返回
//	public static final String CMD_WJ = "WJ";           //问卷调查
	
	//与M3交互命令码----------------------------------------------------------
	public static final String CMD_DD = "DD";			//显示密码框
	public static final String CMD_CD = "CD";			//关闭密码框
	public static final String CMD_XS = "XS";			//显示密码信息
	
	//返回码----------------------------------------------------------------
	public static final String RETURN_JT = "JT";
	public static final String RETURN_JG = "JG";
	public static final String RETURN_PK = "PK";
	public static final String RETURN_SH = "SH";
	public static final String RETURN_IO = "IO";
	public static final String RETURN_AG = "AG";
	public static final String RETURN_DG = "DG";
	public static final String RETURN_QG = "QG";
	public static final String RETURN_SQ = "SQ";
	public static final String RETURN_LH = "LH";
	public static final String RETURN_LP = "LP";
	public static final String RETURN_QT = "QT";
	public static final String RETURN_SU = "SU";
	public static final String RETURN_VI = "VI";
	public static final String RETURN_QB = "QB";
	public static final String RETURN_QN = "QN";
	public static final String RETURN_QD = "QD";
	public static final String RETURN_WD = "WD";
	public static final String RETURN_QQ = "QQ";
	public static final String RETURN_WQ = "WQ";
	public static final String RETURN_RT = "RT";
	public static final String RETURN_RC = "RC";
	public static final String RETURN_GT = "GT";
	public static final String RETURN_SN = "SN";
	public static final String RETURN_SJ = "SJ";
	public static final String RETURN_WH = "WH";
	public static final String RETURN_DE = "DE";
	public static final String RETURN_CE = "CE";
	public static final String RETURN_XT = "XT";
	
	//操作码--------------------------------------------------------------------
	public static final String OPERATION_JU = "JU";
	public static final String OPERATION_JH = "JH";
	public static final String OPERATION_PL = "PL";
	public static final String OPERATION_SI = "SI";
	public static final String OPERATION_IP = "IP";
	
	//demo应答码--------------------------------------------------------------------
	public static final String RESPONSE_SI = "SJ";
	
	//确认或错误码----------------------------------------------------------------
	public static final String CODE_00 = "00";
	public static final String CODE_01 = "01";
	public static final String CODE_02 = "02";
	public static final String CODE_10 = "10";
	public static final String CODE_12 = "12";
	public static final String CODE_20 = "20";
	public static final String CODE_80 = "80";
	

	public static String getReturnCmd(String curCmd){
		if(CMD_AF.equals(curCmd)){
			return RETURN_AG;
//		}else if(CMD_DF.equals(curCmd)){
//			return RETURN_DG;
//		}else if(CMD_QF.equals(curCmd)){
//			return RETURN_QG;
//		}else if(CMD_JS.equals(curCmd)){
//			return RETURN_JT;
//		}else if(CMD_SP.equals(curCmd)){
//			return RETURN_SQ;
		}else if(CMD_LG.equals(curCmd)){
			return RETURN_LH;
//		}else if(CMD_LO.equals(curCmd)){
//			return RETURN_LP;
//		}else if(CMD_PJ.equals(curCmd)){
//			return RETURN_PK;
//		}else if(CMD_SG.equals(curCmd)){
//			return RETURN_SH;
//		}else if(CMD_IN.equals(curCmd)){
//			return RETURN_IO;
//		}else if(CMD_QS.equals(curCmd)){
//			return RETURN_QT;
//		}else if(CMD_ST.equals(curCmd)){
//			return RETURN_SU;
//		}else if(CMD_UV.equals(curCmd)){
//			return RETURN_VI;
//		}else if(CMD_QA.equals(curCmd)){
//			return RETURN_QB;
//		}else if(CMD_QM.equals(curCmd)){
//			return RETURN_QN;
//		}else if(CMD_QC.equals(curCmd)){
//			return RETURN_QD;
		}else if(CMD_WC.equals(curCmd)){
			return RETURN_WD;
		}else if(CMD_QP.equals(curCmd)){
			return RETURN_QQ;
		}else if(CMD_WP.equals(curCmd)){
			return RETURN_WQ;
//		}else if(CMD_RS.equals(curCmd)){
//			return RETURN_RT;
//		}else if(CMD_RB.equals(curCmd)){
//			return RETURN_RC;
//		}else if(CMD_GS.equals(curCmd)){
//			return RETURN_GT;
//		}else if(CMD_SM.equals(curCmd)){
//			return RETURN_SN;
//		}else if(CMD_SI.equals(curCmd)){
//			return RETURN_SJ;
//		}else if(CMD_WJ.equals(curCmd)){
//			return RETURN_WH;
//		}else if(CMD_DD.equals(curCmd)){
			
		}else if(CMD_CD.equals(curCmd)){
			
		}else if(CMD_XS.equals(curCmd)){
			
		}
		return null;
	}
	public static String getOperationCmd(String cmd){
//		if(CMD_JS.equals(cmd)){
//			return OPERATION_JU;
//		}else if(CMD_PJ.equals(cmd)){
//			return OPERATION_PL;
//		}else if(CMD_IN.equals(cmd)){
//			return OPERATION_IP;
//		}else if(CMD_SG.equals(cmd)){
//			return OPERATION_SI;
//		}else if(CMD_WJ.equals(cmd)){
//			return RETURN_WH;
//		}
		return null;
	}
}
