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

            Member member2 = new Member();
            member2.setAge(22);
            member2.setUsername("member2");
            em.persist(member2);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> result1 = query1.getResultList();
            for (Member member : result1) {
                System.out.println(member.getUsername());
            }


            Query query2 = em.createQuery("select m.age, m.username from Member m");
            List result2 = query2.getResultList();

            Member singleResult = em.createQuery("select m from Member m where m.username =: username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println(singleResult.getUsername());

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
