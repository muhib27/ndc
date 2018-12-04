package com.classtune.ndc.apiresponse.menu_api;

/**
 * Created by Muhib on 12/4/2018.
 */

public class UserPermission {
    public boolean tasksList;
    public boolean tasksAdd;
    public boolean tasksEdit;
    public boolean tasksDelete;
    public boolean tasksView;
    public boolean tasksSubmittedList;
    public boolean tasksSingleView;
    public boolean userAdd;
    public boolean userEdit;
    public boolean userDelete;

    public UserPermission() {
    }

    public boolean isTasksList() {
        return tasksList;
    }

    public void setTasksList(boolean tasksList) {
        this.tasksList = tasksList;
    }

    public boolean isTasksAdd() {
        return tasksAdd;
    }

    public void setTasksAdd(boolean tasksAdd) {
        this.tasksAdd = tasksAdd;
    }

    public boolean isTasksEdit() {
        return tasksEdit;
    }

    public void setTasksEdit(boolean tasksEdit) {
        this.tasksEdit = tasksEdit;
    }

    public boolean isTasksDelete() {
        return tasksDelete;
    }

    public void setTasksDelete(boolean tasksDelete) {
        this.tasksDelete = tasksDelete;
    }

    public boolean isTasksView() {
        return tasksView;
    }

    public void setTasksView(boolean tasksView) {
        this.tasksView = tasksView;
    }

    public boolean isTasksSubmittedList() {
        return tasksSubmittedList;
    }

    public void setTasksSubmittedList(boolean tasksSubmittedList) {
        this.tasksSubmittedList = tasksSubmittedList;
    }

    public boolean isTasksSingleView() {
        return tasksSingleView;
    }

    public void setTasksSingleView(boolean tasksSingleView) {
        this.tasksSingleView = tasksSingleView;
    }

    public boolean isUserAdd() {
        return userAdd;
    }

    public void setUserAdd(boolean userAdd) {
        this.userAdd = userAdd;
    }

    public boolean isUserEdit() {
        return userEdit;
    }

    public void setUserEdit(boolean userEdit) {
        this.userEdit = userEdit;
    }

    public boolean isUserDelete() {
        return userDelete;
    }

    public void setUserDelete(boolean userDelete) {
        this.userDelete = userDelete;
    }
}
