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
            Member member = new Member();
            member.setAge(10);
            member.setUsername("관리자");
            em.persist(member);

            em.flush();
            em.clear();


            String query = "select " +
                                "case when m.age <= 10 then '학생요금'" +
                                "     when m.age >= 60 then '경로요금'" +
                                     "else '일반요금' end " +
                           "from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println(s);
            }

            String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> result2 = em.createQuery(query2, String.class)
                    .getResultList();
            for (String s : result2) {
                System.out.println(s);
            }

            String query3 = "select nullif(m.username, '관리자') from Member m";
            List<String> result3 = em.createQuery(query3, String.class)
                    .getResultList();
            for (String s : result3) {
                System.out.println(s);
            }

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
