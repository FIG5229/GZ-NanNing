/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-精神病患者基本信息Entity
 * @author alan.wu
 * @version 2020-11-20
 */
public class Ren001 extends DataEntity<Ren001> {
	
	private static final long serialVersionUID = 1L;
	private String xm;		// xm
	private String cym;		// cym
	private String zjlx;		// zjlx
	private String zjh;		// zjh
	private String xb;		// xb
	private String xx;		// xx
	private String mz;		// mz
	private String gj;		// gj
	private String hyzk;		// hyzk
	private String zc;		// zc
	private Date csrq;		// csrq
	private String sg;		// cm
	private String whcd;		// whcd
	private String zzmm;		// zzmm
	private String zjxy;		// zjxy
	private String hjdssqx;		// hjdssqx
	private String xzzssqx;		// xzzssqx
	private String hjdxz;		// hjdxz
	private String xzzxz;		// xzzxz
	private String gddh;		// gddh
	private String qtlxhm;		// qtlxhm
	private String sjhm;		// sjhm
	private Date cjsj;		// cjsj
	private String cjr;		// cjr
	private String cjrjh;		// cjrjh
	private String cjdw;		// cjdw
	private Date xgsj;		// xgsj
	private String xgr;		// xgr
	private String xgrjh;		// xgrjh
	private String xgdw;		// xgdw
	private String xgdwdm;		// xgdwdm
	private String nl;		// nl
	private String sfczrk;		// sfczrk
	private String impmark;		// impmark
	private String hjd;		// hjd
	private String xmpy;		// xmpy
	private String chpy;		// chpy
	private String tz;		// tz
	private String bm;		// bm
	private String cym2;		// cym2
	private String hjdpcs;		// hjdpcs
	private String jzdpcs;		// jzdpcs
	private String jg;		// jg
	private String kpfs;		// kpfs
	private String ly;		// ly
	private String wxh;		// wxh
	private String qqh;		// qqh
	private String zfbzh;		// zfbzh
	private String wbh;		// wbh
	private String qtwlxx;		// qtwlxx
	private String cjdwdm;		// cjdwdm
	
	public Ren001() {
		super();
	}

	public Ren001(String id){
		super(id);
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	public String getCym() {
		return cym;
	}

	public void setCym(String cym) {
		this.cym = cym;
	}
	
	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	
	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}
	
	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
	
	public String getXx() {
		return xx;
	}

	public void setXx(String xx) {
		this.xx = xx;
	}
	
	public String getMz() {
		return mz;
	}

	public void setMz(String mz) {
		this.mz = mz;
	}
	
	public String getGj() {
		return gj;
	}

	public void setGj(String gj) {
		this.gj = gj;
	}
	
	public String getHyzk() {
		return hyzk;
	}

	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}
	
	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCsrq() {
		return csrq;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}
	
	public String getSg() {
		return sg;
	}

	public void setSg(String sg) {
		this.sg = sg;
	}
	
	public String getWhcd() {
		return whcd;
	}

	public void setWhcd(String whcd) {
		this.whcd = whcd;
	}
	
	public String getZzmm() {
		return zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}
	
	public String getZjxy() {
		return zjxy;
	}

	public void setZjxy(String zjxy) {
		this.zjxy = zjxy;
	}
	
	public String getHjdssqx() {
		return hjdssqx;
	}

	public void setHjdssqx(String hjdssqx) {
		this.hjdssqx = hjdssqx;
	}
	
	public String getXzzssqx() {
		return xzzssqx;
	}

	public void setXzzssqx(String xzzssqx) {
		this.xzzssqx = xzzssqx;
	}
	
	public String getHjdxz() {
		return hjdxz;
	}

	public void setHjdxz(String hjdxz) {
		this.hjdxz = hjdxz;
	}
	
	public String getXzzxz() {
		return xzzxz;
	}

	public void setXzzxz(String xzzxz) {
		this.xzzxz = xzzxz;
	}
	
	public String getGddh() {
		return gddh;
	}

	public void setGddh(String gddh) {
		this.gddh = gddh;
	}
	
	public String getQtlxhm() {
		return qtlxhm;
	}

	public void setQtlxhm(String qtlxhm) {
		this.qtlxhm = qtlxhm;
	}
	
	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	public String getCjrjh() {
		return cjrjh;
	}

	public void setCjrjh(String cjrjh) {
		this.cjrjh = cjrjh;
	}
	
	public String getCjdw() {
		return cjdw;
	}

	public void setCjdw(String cjdw) {
		this.cjdw = cjdw;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
	
	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	
	public String getXgrjh() {
		return xgrjh;
	}

	public void setXgrjh(String xgrjh) {
		this.xgrjh = xgrjh;
	}
	
	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}
	
	public String getXgdwdm() {
		return xgdwdm;
	}

	public void setXgdwdm(String xgdwdm) {
		this.xgdwdm = xgdwdm;
	}
	
	public String getNl() {
		return nl;
	}

	public void setNl(String nl) {
		this.nl = nl;
	}
	
	public String getSfczrk() {
		return sfczrk;
	}

	public void setSfczrk(String sfczrk) {
		this.sfczrk = sfczrk;
	}
	
	public String getImpmark() {
		return impmark;
	}

	public void setImpmark(String impmark) {
		this.impmark = impmark;
	}
	
	public String getHjd() {
		return hjd;
	}

	public void setHjd(String hjd) {
		this.hjd = hjd;
	}
	
	public String getXmpy() {
		return xmpy;
	}

	public void setXmpy(String xmpy) {
		this.xmpy = xmpy;
	}
	
	public String getChpy() {
		return chpy;
	}

	public void setChpy(String chpy) {
		this.chpy = chpy;
	}
	
	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}
	
	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}
	
	public String getCym2() {
		return cym2;
	}

	public void setCym2(String cym2) {
		this.cym2 = cym2;
	}
	
	public String getHjdpcs() {
		return hjdpcs;
	}

	public void setHjdpcs(String hjdpcs) {
		this.hjdpcs = hjdpcs;
	}
	
	public String getJzdpcs() {
		return jzdpcs;
	}

	public void setJzdpcs(String jzdpcs) {
		this.jzdpcs = jzdpcs;
	}
	
	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}
	
	public String getKpfs() {
		return kpfs;
	}

	public void setKpfs(String kpfs) {
		this.kpfs = kpfs;
	}
	
	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}
	
	public String getWxh() {
		return wxh;
	}

	public void setWxh(String wxh) {
		this.wxh = wxh;
	}
	
	public String getQqh() {
		return qqh;
	}

	public void setQqh(String qqh) {
		this.qqh = qqh;
	}
	
	public String getZfbzh() {
		return zfbzh;
	}

	public void setZfbzh(String zfbzh) {
		this.zfbzh = zfbzh;
	}
	
	public String getWbh() {
		return wbh;
	}

	public void setWbh(String wbh) {
		this.wbh = wbh;
	}
	
	public String getQtwlxx() {
		return qtwlxx;
	}

	public void setQtwlxx(String qtwlxx) {
		this.qtwlxx = qtwlxx;
	}
	
	public String getCjdwdm() {
		return cjdwdm;
	}

	public void setCjdwdm(String cjdwdm) {
		this.cjdwdm = cjdwdm;
	}
	
}