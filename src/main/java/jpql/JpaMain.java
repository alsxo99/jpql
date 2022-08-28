package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setAge(24);
            member1.setUsername("member1");
            em.persist(member1);

            em.createQuery("select m from Member m", Member.class)
                            .getResultList();
            em.createQuery("select t from Member m join m.team t")
                            .getResultList();
            em.createQuery("select o.address from Order o", Address.class)
                            .getResultList();
            List resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            List<MemberDTO> resultList2 = em.createQuery("select distinct new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            Object o = resultList.get(0);
            Object[] result = (Object[]) o;

            MemberDTO memberDTO = resultList2.get(0);
            System.out.println(memberDTO.getUsername());
            System.out.println(memberDTO.getAge());

//            Object[] result1 = resultList1.get(0);
//            System.out.println(result1[0]);
//            System.out.println(result1[1]);

//            System.out.println(result[0]);
//            System.out.println(result[1]);
//
//            for (Object o1 : result) {
//                System.out.println(o1);
//            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
