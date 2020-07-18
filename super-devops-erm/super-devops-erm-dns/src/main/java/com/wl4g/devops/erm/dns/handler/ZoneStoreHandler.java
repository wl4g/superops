package com.wl4g.devops.erm.dns.handler;

import com.wl4g.devops.common.bean.erm.DnsPrivateResolution;
import com.wl4g.devops.common.bean.erm.DnsPrivateZone;

import java.util.Set;

/**
 * @author vjay
 * @date 2020-07-02 11:40:00
 */
public interface ZoneStoreHandler {

	void putDomian(DnsPrivateZone domian);

	void putHost(DnsPrivateZone domain, DnsPrivateResolution resolution);

	void delhost(String domian, String host);

	void delDomain(String domian);

	/////

	void addDnsPrivateBlacklist(String black, String white);

	void removeDnsPrivateBlacklist(String black, String white);

	void reloadDnsPrivateBlacklist(Set<String> blacks, Set<String> whites);

}
