package tr1nks.service.domain.impl;


import org.springframework.stereotype.Service;
import tr1nks.domain.entity.DomainsEntity;
import tr1nks.domain.repository.DomensRepository;
import tr1nks.service.domain.DomainsService;

import javax.annotation.Resource;

@Service
public class DomainsServiceImpl implements DomainsService {
    @Resource
    private DomensRepository domensRepository;
    private static String emailDomen;
    private static String imagineDomen;
    private static String officeDomen;
    @Override
    public String getEmailDomen() {
        if (null == emailDomen) {
            emailDomen = domensRepository.getFirst().getEmailDomen();
        }
        return emailDomen;
    }

    @Override
    public String getImagineDomen() {
        if (null == imagineDomen) {
            imagineDomen = domensRepository.getFirst().getImagineDomen();
        }
        return imagineDomen;
    }

    @Override
    public String getOfficeDomen() {
        if (null == officeDomen) {
            officeDomen = domensRepository.getFirst().getOfficeDomen();
        }
        return officeDomen;
    }

    @Override
    public DomainsEntity save(DomainsEntity domainsEntity) {
        return domensRepository.saveAndFlush(domainsEntity);
    }
}
