package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.UserDAO;
import com.pojo.User;
import com.resource.DBConnection;

public class UserDAOImpl implements UserDAO
{

    private Connection conn;

    @Override
    public int addUser(User user)
    {
        final String sql = "insert into T_USER(id, passwd, name, dept, telephone, status) values(?,?,?,?,?,?)";
        PreparedStatement st = null;
        int res = 0;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, user.getId());
            st.setString(2, user.getPasswd());
            st.setString(3, user.getName());
            st.setInt(4, user.getDept());
            st.setString(5, user.getTelephone());
            st.setString(6, user.getStatus());
            st.execute();
            // conn.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
            res = 1;
        } finally
        {
            try
            {
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public int updateUserById(String id, User user)
    {
        String sql = "update T_USER SET passwd=?, name=?, dept=?, telephone=?, status=? where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, user.getPasswd());
            st.setString(2, user.getName());
            st.setInt(3, user.getDept());
            st.setString(4, user.getTelephone());
            st.setString(5, user.getStatus());
            st.setString(6, id);
            st.execute();
            // conn.commit();
        }// UPDATE t_user SET passwd = 'Fred' WHERE passwd = 'asd';
        catch (SQLException e)
        {
            e.printStackTrace();
            return 1;
        } finally
        {
            try
            {
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int deleteUserById(String id)
    {
        String sql = "delete from T_USER where id=?";
        PreparedStatement st = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, id);
            st.execute();
            // conn.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return 1;
        } finally
        {
            try
            {
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public User queryUserByPwd(String id, String passwd)
    {
        String sql = "select id,passwd,name,dept,telephone,status from T_USER where id=? and passwd=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, passwd);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                user = new User();
                user.setId(rs.getString(1));
                user.setPasswd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDept(rs.getInt(4));
                user.setTelephone(rs.getString(5));
                user.setStatus(rs.getString(6));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            user = null;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User queryUserById(String id)
    {
        String sql = "select id,passwd,name,dept,telephone,status from T_USER where id=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                user = new User();
                user.setId(rs.getString(1));
                user.setPasswd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDept(rs.getInt(4));
                user.setTelephone(rs.getString(5));
                user.setStatus(rs.getString(6));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            user = null;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return user;

    }

    @Override
    public List<User> queryUserByName(String name)
    {
        String sql = "select id,passwd,name,dept,telephone,status from T_USER where name=?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> userList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs != null)
            {
                userList = new ArrayList<User>();
                while (rs.next())
                {
                    User user = new User();
                    user.setId(rs.getString(1));
                    user.setPasswd(rs.getString(2));
                    user.setName(rs.getString(3));
                    user.setDept(rs.getInt(4));
                    user.setTelephone(rs.getString(5));
                    user.setStatus(rs.getString(6));
                    userList.add(user);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            userList = null;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public int getUserSum()
    {
        int sum = 0;
        String sql = "select count(1) from T_USER";
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null && rs.next())
            {
                sum = rs.getInt(1);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            sum = 0;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return sum;
    }

    @Override
    public List<User> getAllUser()
    {
        String sql = "select id,passwd,name,dept,telephone, status from T_USER";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> userList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs != null)
            {
                userList = new ArrayList<User>();
                while (rs.next())
                {
                    User user = new User();
                    user.setId(rs.getString(1));
                    user.setPasswd(rs.getString(2));
                    user.setName(rs.getString(3));
                    user.setDept(rs.getInt(4));
                    user.setTelephone(rs.getString(5));
                    user.setStatus(rs.getString(6));
                    userList.add(user);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            userList = null;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public List<User> queryUserByDept(int dept)
    {
        String sql = "select id,passwd,name,dept,telephone,status from T_USER where dept = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> userList = null;
        try
        {
            conn = DBConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, dept);
            rs = st.executeQuery();
            if (rs != null)
            {
                userList = new ArrayList<User>();
                while (rs.next())
                {
                    User user = new User();
                    user.setId(rs.getString(1));
                    user.setPasswd(rs.getString(2));
                    user.setName(rs.getString(3));
                    user.setDept(rs.getInt(4));
                    user.setTelephone(rs.getString(5));
                    user.setStatus(rs.getString(6));
                    userList.add(user);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            userList = null;
        } finally
        {
            try
            {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return userList;

    }

}
