package tr1nks.service.domain.impl;


import org.springframework.stereotype.Service;
import tr1nks.domain.entity.DomainsEntity;
import tr1nks.domain.repository.DomainsRepository;
import tr1nks.service.domain.DomainsService;

import javax.annotation.Resource;

@Service
public class DomainsServiceImpl implements DomainsService {
    @Resource
    private DomainsRepository domainsRepository;
    private static String emailDomen;
    private static String imagineDomen;
    private static String officeDomen;
    @Override
    public String getEmailDomain() {
        if (null == emailDomen) {
            emailDomen = domainsRepository.getFirst().getEmailDomen();
        }
        return emailDomen;
    }

    @Override
    public String getImagineDomain() {
        if (null == imagineDomen) {
            imagineDomen = domainsRepository.getFirst().getImagineDomen();
        }
        return imagineDomen;
    }

    @Override
    public String getOfficeDomain() {
        if (null == officeDomen) {
            officeDomen = domainsRepository.getFirst().getOfficeDomen();
        }
        return officeDomen;
    }

    @Override
    public DomainsEntity save(DomainsEntity domainsEntity) {
        return domainsRepository.saveAndFlush(domainsEntity);
    }
}
